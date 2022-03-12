package com.example.offliner.controller;

import com.example.offliner.domain.Message;
import com.example.offliner.domain.Role;
import com.example.offliner.domain.User;
import com.example.offliner.repos.MessageRepo;
import com.example.offliner.repos.RateRepo;
import com.example.offliner.repos.UserRepo;
import com.example.offliner.service.RateService;
import com.example.offliner.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private UserSevice userSevice;
    @Autowired
    private RateRepo rateRepo;
    @Autowired
    private RateService rateService;

    @Value("${upload.path}")
    private String uploadPath;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepo.findAll());

        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);

        return "redirect:/user";
    }

    @GetMapping("/profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        Iterable<Message> messages = messageRepo.findAll();
        ArrayList<Message> messages1 = new ArrayList<>();
        Integer counter = 0;
        for(Message message:messages){
            if(Objects.equals(message.getAuthor().getUsername(), user.getUsername())){
                messages1.add(message);
                counter++;
            }
        }
        for(Message message : messages){
            message.setAverageRate(rateService.calcAverageRate(message));
        }
        Collections.reverse(messages1);
        model.addAttribute("countOfPosts",counter);
        model.addAttribute("user", user);
        model.addAttribute("aboutMyself",user.getAboutMyself());
        model.addAttribute("messages",messages1);

        return "profile";
    }

    @GetMapping("/profile/{username}/settings")
    public String settings(
            Model model, @PathVariable String username
    ){
        User user = userRepo.findByUsername(username);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("aboutMyself",user.getAboutMyself());
        model.addAttribute("linkFacebook",user.getLinkFacebook());
        model.addAttribute("linkGoogle",user.getLinkGoogle());
        model.addAttribute("linkYoutube",user.getLinkYoutube());
        model.addAttribute("linkDribble",user.getLinkDribble());
        model.addAttribute("linkLinkedIn",user.getLinkLinkedIn());
        return "settings";
    }

    @PostMapping("/profile/{username}/settings")
    public String updateProfile(
            @PathVariable String username,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam String aboutMyself,
            @RequestParam String linkFacebook,
            @RequestParam String linkGoogle,
            @RequestParam String linkYoutube,
            @RequestParam String linkDribble,
            @RequestParam String linkLinkedIn,
            @RequestParam("file") MultipartFile file

    ) throws IOException {
        User user = userRepo.findByUsername(username);
        userSevice.updateProfile(user, password, email,aboutMyself,linkFacebook,linkGoogle,linkYoutube,linkDribble,linkLinkedIn,file);

        return "redirect:/user/profile";
    }

    @GetMapping("/profile/{id}/{username}")
    public String userProfile(
            Model model, @PathVariable String username,@PathVariable Long id,@AuthenticationPrincipal User user1){
        Iterable<Message> messages = messageRepo.findAll();
        User user = userRepo.findByUsername(username);
        ArrayList<Message> messages1 = new ArrayList<Message>();
        Integer counter = 0;
        for(Message message:messages){
            if(Objects.equals(message.getAuthor().getUsername(), user.getUsername())){
                messages1.add(message);
                counter++;
            }
        }
        for(Message message : messages){
            message.setAverageRate(rateService.calcAverageRate(message));
        }
        boolean admin;
        admin = user1.isAdmin();
        Collections.reverse(messages1);
        model.addAttribute("countOfPosts",counter);
        model.addAttribute("admin",admin);
        model.addAttribute("user", user);
        model.addAttribute("messages", messages1);
        return "userProfile";
    }

    @PostMapping("/profile/{username}/settings/delete")
    public String deleteUser(
            @PathVariable String username,
            @AuthenticationPrincipal User user1
    ){
        User user = userRepo.findByUsername(username);
        userRepo.delete(user);
        if (user1.isAdmin()) {
            return "redirect:/user";
        } else {
            return "redirect:/login";
        }
    }


}

package com.example.offliner.controller;

import com.example.offliner.domain.Message;
import com.example.offliner.domain.Role;
import com.example.offliner.domain.User;
import com.example.offliner.repos.MessageRepo;
import com.example.offliner.repos.UserRepo;
import com.example.offliner.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/user/profile/{username}")
    public String getProfile(Model model, @PathVariable String username) {
        Iterable<Message> messages = messageRepo.findAll();
        User user = userRepo.findByUsername(username);
        ArrayList<Message> messages1 = new ArrayList<Message>();
        for(Message message:messages){
            if(Objects.equals(message.getAuthor().getUsername(), user.getUsername())){
                messages1.add(message);
            }
        }
        Collections.reverse(messages1);
        model.addAttribute("user", user);
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
        return "settings";
    }

    @PostMapping("/profile/{username}/settings")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam String aboutMyself
    ) {
        userSevice.updateProfile(user, password, email,aboutMyself);

        return "redirect:/main";
    }

}

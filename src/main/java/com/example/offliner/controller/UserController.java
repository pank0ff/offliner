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

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepo.findAll());

        return "userList";
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        return "userEdit";
    }

    @PreAuthorize("hasAuthority('USER')")
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

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        Iterable<Message> messages = messageRepo.findAll();
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
    @GetMapping("/user/{id}/settings")
    public String settings(
            Model model, @AuthenticationPrincipal User user
    ){
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "settings";
    }

    @PostMapping("/user/{id}/settings")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email
    ) {
        userSevice.updateProfile(user, password, email);

        return "redirect:/user/profile";
    }
}

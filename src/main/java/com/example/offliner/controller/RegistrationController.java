package com.example.offliner.controller;

import com.example.offliner.domain.User;
import com.example.offliner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class RegistrationController {
    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String choice, @RequestParam String theme, User user, Map<String, Object> model) {
        if (userService.getUserByUsername(user.getUsername()) != null) {
            model.put("message", "User exists!");
            return "registration";
        }
        userService.addUser(user, theme, choice);
        return "redirect:/login";
    }
}

package com.example.offliner.controller;

import com.example.offliner.domain.Message;
import com.example.offliner.domain.Role;
import com.example.offliner.domain.User;
import com.example.offliner.repos.MessageRepo;
import com.example.offliner.repos.UserRepo;
import com.example.offliner.service.RateService;
import com.example.offliner.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
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
    private RateService rateService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(@AuthenticationPrincipal User user, Model model) {

        boolean userChoice = Objects.equals(user.getChoice(), "ENG");
        boolean theme = Objects.equals(user.getTheme(), "LIGHT");
        model.addAttribute("theme", theme);
        model.addAttribute("lang", userChoice);
        model.addAttribute("users", userRepo.findAll());

        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, @AuthenticationPrincipal User user1, Model model) {
        boolean userChoice = Objects.equals(user1.getChoice(), "ENG");
        boolean theme = Objects.equals(user1.getTheme(), "LIGHT");
        model.addAttribute("theme", theme);
        model.addAttribute("lang", userChoice);
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
        Integer userLikes = 0;
        for (Message message : messages) {
            message.setLikesCount(message.getLikes().size());
        }
        Integer counter = 0;
        for (Message message : messages) {
            if (Objects.equals(message.getAuthor().getUsername(), user.getUsername())) {
                messages1.add(message);
                userLikes += message.getLikesCount();
                counter++;
            }
        }
        user.setCountOfLikes(userLikes);
        user.setCountOfPosts(counter);
        for (Message message : messages) {
            message.setAverageRate(rateService.calcAverageRate(message));
        }
        Collections.reverse(messages1);
        boolean userChoice = Objects.equals(user.getChoice(), "ENG");
        boolean theme = Objects.equals(user.getTheme(), "LIGHT");
        model.addAttribute("userLikes", userLikes);
        model.addAttribute("theme", theme);
        model.addAttribute("lang", userChoice);
        model.addAttribute("countOfPosts", counter);
        model.addAttribute("user", user);
        model.addAttribute("aboutMyself", user.getAboutMyself());
        model.addAttribute("messages", messages1);

        return "profile";
    }

    @GetMapping("/profile/{username}/settings")
    public String settings(
            Model model, @PathVariable String username, @AuthenticationPrincipal User userCurrent
    ) {

        User user = userRepo.findByUsername(username);
        boolean userChoice = Objects.equals(userCurrent.getChoice(), "ENG");
        boolean theme = Objects.equals(userCurrent.getTheme(), "LIGHT");

        model.addAttribute("theme", theme);
        model.addAttribute("lang", userChoice);
        model.addAttribute("userChoice", user.getChoice());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("aboutMyself", user.getAboutMyself());
        model.addAttribute("linkFacebook", user.getLinkFacebook());
        model.addAttribute("linkGoogle", user.getLinkGoogle());
        model.addAttribute("linkYoutube", user.getLinkYoutube());
        model.addAttribute("linkDribble", user.getLinkDribble());
        model.addAttribute("linkLinkedIn", user.getLinkLinkedIn());
        return "settings";
    }

    @PostMapping("/profile/{username}/settings")
    public String updateProfile(
            @PathVariable String username,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam String aboutMyself,
            @RequestParam String userChoice,
            @RequestParam String theme,
            @RequestParam String linkFacebook,
            @RequestParam String linkGoogle,
            @RequestParam String linkYoutube,
            @RequestParam String linkDribble,
            @RequestParam String linkLinkedIn,
            @RequestParam("file") MultipartFile file

    ) throws IOException {
        User user = userRepo.findByUsername(username);
        userSevice.updateProfile(user, password, email, aboutMyself, userChoice, theme, linkFacebook, linkGoogle, linkYoutube, linkDribble, linkLinkedIn, file);

        return "redirect:/user/profile";
    }

    @GetMapping("/profile/{id}/{username}")
    public String userProfile(
            Model model, @PathVariable String username, @PathVariable Long id, @AuthenticationPrincipal User currentUser) {
        Iterable<Message> messages = messageRepo.findAll();
        User user = userRepo.findByUsername(username);
        ArrayList<Message> messages1 = new ArrayList<>();
        Integer userLikes = 0;
        for (Message message : messages) {
            message.setLikesCount(message.getLikes().size());
        }
        Integer counter = 0;
        for (Message message : messages) {
            if (Objects.equals(message.getAuthor().getUsername(), user.getUsername())) {
                messages1.add(message);
                userLikes += message.getLikesCount();
                counter++;
            }
        }
        user.setCountOfLikes(userLikes);
        user.setCountOfPosts(counter);
        for (Message message : messages) {
            message.setAverageRate(rateService.calcAverageRate(message));
        }
        boolean isCurrentUser = Objects.equals(user.getUsername(), currentUser.getUsername());
        boolean isSubscriber = false;
        boolean admin;
        admin = currentUser.isAdmin();
        Collections.reverse(messages1);
        for (User user2 : user.getSubscribers()) {
            if (Objects.equals(user2.getUsername(), currentUser.getUsername())) {
                isSubscriber = true;
            }
        }
        boolean userChoice = Objects.equals(currentUser.getChoice(), "ENG");
        boolean theme = Objects.equals(currentUser.getTheme(), "LIGHT");
        model.addAttribute("userLikes", userLikes);
        model.addAttribute("isCurrentUser", isCurrentUser);
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("theme", theme);
        model.addAttribute("isSubscriber", isSubscriber);
        model.addAttribute("lang", userChoice);
        model.addAttribute("countOfPosts", counter);
        model.addAttribute("admin", admin);
        model.addAttribute("user", user);
        model.addAttribute("messages", messages1);
        return "userProfile";
    }

    @PostMapping("/profile/{username}/settings/delete")
    public String deleteUser(
            @PathVariable String username,
            @AuthenticationPrincipal User user1
    ) {
        User user = userRepo.findByUsername(username);
        userRepo.delete(user);
        if (user1.isAdmin()) {
            return "redirect:/user";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("subscribe/{user}")
    public String subscribe(@PathVariable User user, @AuthenticationPrincipal User currentUser) {
        userSevice.subscribe(currentUser, user);
        return "redirect:/user/profile/" + user.getId() + "/" + user.getUsername();
    }

    @GetMapping("/unsubscribe/{user}")
    public String unsubscribe(@PathVariable User user, @AuthenticationPrincipal User currentUser) {
        userSevice.unsubscribe(currentUser, user);
        return "redirect:/user/profile/" + user.getId() + "/" + user.getUsername();
    }

    @GetMapping("/like/{messageId}")
    public String like(@PathVariable Integer messageId, @AuthenticationPrincipal User user) {
        Message message = messageRepo.findById(messageId);
        userSevice.like(user, message);
        return "redirect:/post/" + message.getId();
    }

    @GetMapping("/unlike/{messageId}")
    public String unlike(@PathVariable Integer messageId, @AuthenticationPrincipal User user) {
        Message message = messageRepo.findById(messageId);
        userSevice.unlike(user, message);
        return "redirect:/post/" + message.getId();
    }

    @GetMapping("{type}/{user}/list")
    public String userListOf(Model model,
                             @PathVariable User user,
                             @PathVariable String type) {
        model.addAttribute("userChannel", user);
        model.addAttribute("type", type);
        if ("subscriptions".equals(type)) {
            model.addAttribute("users", user.getSubscriptions());
        } else {
            model.addAttribute("users", user.getSubscribers());
        }

        return "subscriptions";
    }


}

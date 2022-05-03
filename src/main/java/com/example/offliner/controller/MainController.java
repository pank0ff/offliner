package com.example.offliner.controller;

import com.example.offliner.domain.Comment;
import com.example.offliner.domain.Message;
import com.example.offliner.domain.User;
import com.example.offliner.service.CommentService;
import com.example.offliner.service.MessageService;
import com.example.offliner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@Controller
public class MainController {
    private final MessageService messageService;
    private final UserService userService;
    private final CommentService commentService;

    @Autowired
    public MainController(MessageService messageService, UserService userService, CommentService commentService) {
        this.commentService = commentService;
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping("/")
    public String greeting(@RequestParam(required = false, defaultValue = "") String filter,
                           @RequestParam(required = false, defaultValue = "0") int choice,
                           @RequestParam(required = false, defaultValue = "1") int sortChoice,
                           Model model,
                           @AuthenticationPrincipal User user) {
        List<Message> messages1 = new ArrayList<>();
        List<Message> messages = messageService.getAllMessages();
        messageService.loadMessages(messages, user);
        for (Message message : messages) {
            if (message.getAverageRate() >= 4) {
                messages1.add(message);
            }
        }
        boolean userChoice = true;
        boolean theme = true;
        boolean isAdmin = false;
        if (user != null) {
            userChoice = Objects.equals(user.getChoice(), "ENG");
            theme = Objects.equals(user.getTheme(), "LIGHT");
            isAdmin = user.isAdmin();
        }
        List<Message> messages2 = messageService.sortMessages(choice, filter, messages1);
        switch (sortChoice) {
            case 1:
                Collections.reverse(messages2);
                model.addAttribute("messages", messages2);
                break;
            case 2:
                model.addAttribute("messages", messages2);
                break;
        }
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("theme", theme);
        model.addAttribute("lang", userChoice);
        model.addAttribute("user", user);
        return "greeting";
    }

    @GetMapping("/main")
    public String main(
            @RequestParam(required = false, defaultValue = "") String filter,
            @RequestParam(required = false, defaultValue = "0") int choice,
            @RequestParam(required = false, defaultValue = "1") int sortChoice,
            Model model,
            @AuthenticationPrincipal User user) {
        List<Message> messages = messageService.getAllMessages();
        messageService.loadMessages(messages, user);
        List<Message> messages2 = messageService.sortMessages(choice, filter, messages);
        boolean userChoice = true;
        boolean theme = true;
        boolean isAdmin = false;
        if (user != null) {
            userChoice = Objects.equals(user.getChoice(), "ENG");
            theme = Objects.equals(user.getTheme(), "LIGHT");
            isAdmin = user.isAdmin();
        }
        switch (sortChoice) {
            case 1:
                Collections.reverse(messages2);
                model.addAttribute("messages", messages2);
                break;
            case 2:
                model.addAttribute("messages", messages2);
                break;
        }
        model.addAttribute("theme", theme);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("lang", userChoice);
        model.addAttribute("filter", filter);
        model.addAttribute("user", user);
        return "main";
    }

    @GetMapping("/user/profile/{id}")
    public String filter(@RequestParam(required = false, defaultValue = "") String filter,
                         @RequestParam(required = false, defaultValue = "0") int choice,
                         @RequestParam(required = false, defaultValue = "1") int sortChoice,
                         @PathVariable Long id,
                         Model model
    ) {
        User user = userService.getUserById(id);
        List<Message> messages = messageService.getMessagesByAuthor(user);
        List<Message> messages2 = messageService.sortMessages(choice, filter, messages);
        messageService.loadMessages(messages2, user);
        switch (sortChoice) {
            case 1:
                Collections.reverse(messages2);
                model.addAttribute("messages", messages2);
                break;
            case 2:
                model.addAttribute("messages", messages2);
                break;

        }
        model.addAttribute("countOfSubscribers", user.getSubscribers().size());
        model.addAttribute("countOfSubscriptions", user.getSubscriptions().size());
        model.addAttribute("userLikes", user.getCountOfLikes());
        model.addAttribute("theme", Objects.equals(user.getTheme(), "LIGHT"));
        model.addAttribute("isAdmin", user.isAdmin());
        model.addAttribute("lang", Objects.equals(user.getChoice(), "ENG"));
        model.addAttribute("countOfPosts", userService.getUserCountOfPosts(user));
        model.addAttribute("user", user);
        model.addAttribute("aboutMyself", user.getAboutMyself());
        model.addAttribute("filter", filter);

        return "profile";
    }

    @GetMapping("/user/profile/{username}/else")
    public String filterOnSomeoneElsePage(@RequestParam(required = false, defaultValue = "") String filter,
                                          @RequestParam(required = false, defaultValue = "0") int choice,
                                          @RequestParam(required = false, defaultValue = "1") int sortChoice,
                                          Model model,
                                          @PathVariable String username,
                                          @AuthenticationPrincipal User currentUser) {
        User user = userService.getUserByUsername(username);
        List<Message> messages = messageService.getMessagesByAuthor(user);
        List<Message> messages2 = messageService.sortMessages(choice, filter, messages);
        messageService.loadMessages(messages2, user);
        switch (sortChoice) {
            case 1:
                Collections.reverse(messages2);
                model.addAttribute("messages", messages2);
                break;
            case 2:
                model.addAttribute("messages", messages2);
                break;

        }
        model.addAttribute("isSubscriber", userService.isSubscriber(user, currentUser));
        model.addAttribute("isCurrentUser", Objects.equals(user.getUsername(), currentUser.getUsername()));
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("userLikes", user.getCountOfLikes());
        model.addAttribute("theme", Objects.equals(currentUser.getTheme(), "LIGHT"));
        model.addAttribute("isAdmin", user.isAdmin());
        model.addAttribute("lang", Objects.equals(currentUser.getChoice(), "ENG"));
        model.addAttribute("countOfPosts", userService.getUserCountOfPosts(user));
        model.addAttribute("admin", currentUser.isAdmin());
        model.addAttribute("user", user);
        model.addAttribute("filter", filter);

        return "userProfile";
    }

    @PostMapping("/user/profile/add/{username}")
    public String add(
            @PathVariable String username,
            @RequestParam String text,
            @RequestParam String name,
            @RequestParam String hashtag,
            @RequestParam String tag, Map<String, Object> model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        messageService.addMessage(username, text, name, hashtag, tag, file);
        List<Message> messages = messageService.getAllMessages();
        model.put("messages", messages);
        return "redirect:/user/profile";
    }

    @GetMapping("/post/{id}")
    public String userPost(@PathVariable Integer id, @AuthenticationPrincipal User user, Model model) {
        Message message = messageService.getMessageById(id);
        List<Comment> comments = commentService.getCommentsByMessageId(id);
        Collections.reverse(comments);
        messageService.getPost(message, user);
        model.addAttribute("theme", Objects.equals(user.getTheme(), "LIGHT"));
        model.addAttribute("isAdmin", user.isAdmin());
        model.addAttribute("lang", Objects.equals(user.getChoice(), "ENG"));
        model.addAttribute("user", user);
        model.addAttribute("comments", comments);
        model.addAttribute("message", message);
        return "post";
    }

    @PostMapping("/user/profile/update/{id}")
    public String postUpdate(@PathVariable Integer id,
                             @RequestParam String text,
                             @RequestParam String name,
                             @RequestParam String hashtag,
                             @RequestParam String tag,
                             @RequestParam("file") MultipartFile file) throws IOException {
        messageService.postUpdate(id, text, hashtag, tag, name, file);
        return "redirect:/user/profile";
    }

    @GetMapping("/user/profile/update/{id}")
    public String postUpdateForm(@PathVariable Integer id, Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("theme", Objects.equals(user.getTheme(), "LIGHT"));
        model.addAttribute("lang", Objects.equals(user.getChoice(), "ENG"));
        model.addAttribute("message", messageService.getMessageById(id));
        return "editMess";
    }

    @PostMapping("/user/profile/update/{id}/delete")
    public String deletePost(@PathVariable Integer id) {
        messageService.deleteMessage(id);
        return "redirect:/user/profile";
    }

    @GetMapping("/post/hashtag/{hashtag}")
    public String allByHashtag(@PathVariable String hashtag, Model model, @AuthenticationPrincipal User user) {
        List<Message> messages = messageService.getMessagesByHashtag(hashtag);
        messageService.loadMessages(messages, user);
        model.addAttribute("user", user);
        model.addAttribute("theme", Objects.equals(user.getTheme(), "LIGHT"));
        model.addAttribute("isAdmin", user.isAdmin());
        model.addAttribute("lang", Objects.equals(user.getChoice(), "ENG"));
        model.addAttribute("messages", messages);
        model.addAttribute("hashtag", hashtag);

        return "allByTag";
    }

    @GetMapping("/post/topic/{topic}")
    public String allByTopic(@PathVariable String topic, Model model, @AuthenticationPrincipal User user) {
        List<Message> messages = messageService.getMessagesByTopic(topic);
        messageService.loadMessages(messages, user);
        model.addAttribute("user", user);
        model.addAttribute("theme", Objects.equals(user.getTheme(), "LIGHT"));
        model.addAttribute("isAdmin", user.isAdmin());
        model.addAttribute("lang", Objects.equals(user.getChoice(), "ENG"));
        model.addAttribute("messages", messages);
        model.addAttribute("topic", topic);
        return "byTopic";
    }
}

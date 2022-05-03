package com.example.offliner.controller;

import com.example.offliner.domain.Comment;
import com.example.offliner.domain.Message;
import com.example.offliner.domain.User;
import com.example.offliner.exception.ApiRequestException;
import com.example.offliner.service.CommentService;
import com.example.offliner.service.MessageService;
import com.example.offliner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Validated
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
        messageService.loadMessages(messageService.getAllMessages(), user);
        try {
            List<Message> messages2 = messageService.sortMessages(choice, filter, messageService.searchTopMessage());
            model.addAttribute("messages", messageService.sortByDate(messages2, sortChoice));
            userService.calcUserRateForAll();
        } catch (Exception e) {
            throw new ApiRequestException("Cant sort messages.Check filter, or else fields");
        }
        model.addAttribute("isAdmin", userService.getUserIsAdmin(user));
        model.addAttribute("theme", userService.getUserTheme(user));
        model.addAttribute("lang", userService.getUserLang(user));
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
        messageService.loadMessages(messageService.getAllMessages(), user);
        try {
            List<Message> messages2 = messageService.sortMessages(choice, filter, messageService.getAllMessages());
            model.addAttribute("messages", messageService.sortByDate(messages2, sortChoice));
            userService.calcUserRateForAll();
        } catch (Exception e) {
            throw new ApiRequestException("Cant sort messages. Check filter, or else fields");
        }
        model.addAttribute("isAdmin", userService.getUserIsAdmin(user));
        model.addAttribute("theme", userService.getUserTheme(user));
        model.addAttribute("lang", userService.getUserLang(user));
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
        messageService.sortMessagesWithExceptionCheck(filter, choice, sortChoice, model, user);
        model.addAttribute("countOfSubscribers", user.getSubscribers().size());
        model.addAttribute("countOfSubscriptions", user.getSubscriptions().size());
        model.addAttribute("userLikes", user.getCountOfLikes());
        model.addAttribute("theme", Objects.equals(user.getTheme(), "LIGHT"));
        model.addAttribute("isAdmin", user.isAdmin());
        model.addAttribute("lang", Objects.equals(user.getLang(), "ENG"));
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
        messageService.sortMessagesWithExceptionCheck(filter, choice, sortChoice, model, user);
        model.addAttribute("isSubscriber", userService.isSubscriber(user, currentUser));
        model.addAttribute("isCurrentUser", Objects.equals(user.getUsername(), currentUser.getUsername()));
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("userLikes", user.getCountOfLikes());
        model.addAttribute("theme", Objects.equals(currentUser.getTheme(), "LIGHT"));
        model.addAttribute("isAdmin", user.isAdmin());
        model.addAttribute("lang", Objects.equals(currentUser.getLang(), "ENG"));
        model.addAttribute("countOfPosts", userService.getUserCountOfPosts(user));
        model.addAttribute("admin", currentUser.isAdmin());
        model.addAttribute("user", user);
        model.addAttribute("filter", filter);

        return "userProfile";
    }

    @PostMapping("/user/profile/add/{username}")
    public String add(
            @Valid @PathVariable String username,
            @Valid @RequestParam String text,
            @Valid @RequestParam String name,
            @Valid @RequestParam String hashtag,
            @Valid @RequestParam String tag, Map<String, Object> model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        try {
            messageService.addMessage(username, text, name, hashtag, tag, file);
            model.put("messages", messageService.getAllMessages());
        } catch (Exception e) {
            throw new ApiRequestException("Cant add message. Check your fields");
        }
        return "redirect:/user/profile";
    }

    @GetMapping("/post/{id}")
    public String userPost(@PathVariable Integer id, @AuthenticationPrincipal User user, Model model) {
        Message message = messageService.getMessageById(id);
        List<Comment> comments = commentService.getCommentsByMessageId(id);
        Collections.reverse(comments);
        messageService.getPost(message, user);
        message.getAuthor().setUserRate(userService.calcUserRate(message.getAuthor()));
        model.addAttribute("theme", Objects.equals(user.getTheme(), "LIGHT"));
        model.addAttribute("isAdmin", user.isAdmin());
        model.addAttribute("lang", Objects.equals(user.getLang(), "ENG"));
        model.addAttribute("user", user);
        model.addAttribute("comments", comments);
        model.addAttribute("message", message);
        return "post";
    }

    @PostMapping("/user/profile/update/{id}")
    public String postUpdate(@PathVariable Integer id,
                             @Valid @RequestParam String text,
                             @Valid @RequestParam String name,
                             @Valid @RequestParam String hashtag,
                             @Valid @RequestParam String tag,
                             @RequestParam("file") MultipartFile file) {
        try {
            messageService.postUpdate(id, text, hashtag, tag, name, file);
        } catch (Exception e) {
            throw new ApiRequestException("Cant update message. Check fields");
        }
        return "redirect:/user/profile";
    }

    @GetMapping("/user/profile/update/{id}")
    public String postUpdateForm(@PathVariable Integer id, Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("theme", Objects.equals(user.getTheme(), "LIGHT"));
        model.addAttribute("lang", Objects.equals(user.getLang(), "ENG"));
        model.addAttribute("message", messageService.getMessageById(id));
        return "editMess";
    }

    @PostMapping("/user/profile/update/{id}/delete")
    public String deletePost(@PathVariable Integer id) {
        messageService.deleteMessage(id);
        return "redirect:/user/profile";
    }

    @GetMapping("/post/hashtag/{hashtag}")
    public String allByHashtag(@Valid @PathVariable String hashtag, Model model, @AuthenticationPrincipal User user) {
        List<Message> messages = messageService.getMessagesByHashtag(hashtag);
        messageService.messageLoader(model, user, messages);
        model.addAttribute("hashtag", hashtag);

        return "allByTag";
    }

    @GetMapping("/post/topic/{topic}")
    public String allByTopic(@Valid @PathVariable String topic, Model model, @AuthenticationPrincipal User user) {
        List<Message> messages = messageService.getMessagesByTopic(topic);
        messageService.messageLoader(model, user, messages);
        model.addAttribute("topic", topic);
        return "byTopic";
    }
}

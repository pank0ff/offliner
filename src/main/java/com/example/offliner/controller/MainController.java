package com.example.offliner.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.offliner.domain.Comment;
import com.example.offliner.domain.Message;
import com.example.offliner.domain.User;
import com.example.offliner.repos.CommentRepo;
import com.example.offliner.repos.MessageRepo;
import com.example.offliner.repos.RateRepo;
import com.example.offliner.repos.UserRepo;
import com.example.offliner.service.RateService;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;


@Controller
public class MainController {
    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private RateService rateService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RateRepo rateRepo;

    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dwnzejl4h",
            "api_key", "424976915584458",
            "api_secret", "TlQsPJt2OHBBSJVzwe31u3zFqgY"));

    @GetMapping("/")
    public String greeting(Model model, @AuthenticationPrincipal User user) {
        boolean userChoice = true;
        boolean theme = true;
        if (user != null) {
            userChoice = Objects.equals(user.getChoice(), "ENG");
        }
        if (user != null) {
            theme = Objects.equals(user.getTheme(), "LIGHT");
        }

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
        Iterable<Message> messages = messageRepo.findAll();
        List<User> users = userRepo.findAll();
        List<Message> messages2 = messageRepo.findAll();
        for (Message message : messages) {
            message.setLikesCount(message.getLikes().size());
        }
        for (Message message : messages) {
            message.setMeLiked(0);
        }
        for (User user1 : users) {
            Integer postCount = 0;
            Integer likesCount = 0;
            for (Message message : messages) {
                for (User user3 : message.getLikes()) {
                    if (Objects.equals(user3.getUsername(), user.getUsername())) {
                        message.setMeLiked(1);
                    }
                }
                if (Objects.equals(message.getAuthor().getUsername(), user1.getUsername())) {
                    postCount++;
                    likesCount += message.getLikes().size();
                }
            }
            user1.setCountOfLikes(likesCount);
            user1.setCountOfPosts(postCount);
        }
        messages2.clear();
        switch (choice) {
            case 1:
                if (filter != null && !filter.isEmpty()) {
                    for (Message message : messages) {
                        String[] textOfName = message.getName().split(" ");
                        boolean flag = false;
                        for (String str : textOfName) {
                            if (Objects.equals(str, filter)) {
                                flag = true;
                                break;
                            }
                        }
                        List<Comment> comments = commentRepo.findByMessageId(message.getId());
                        for(Comment comment : comments){
                            String[] textOfComment = comment.getText().split(" ");
                            for(String str : textOfComment){
                                if (Objects.equals(str, filter)) {
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        if(Objects.equals(message.getTag(), filter)){
                            flag = true;
                        }
                        if(Objects.equals(message.getHashtag(),filter)){
                            flag = true;
                        }
                        String[] textOfMessage = message.getText().split(" ");
                        for(String str : textOfMessage){
                            if (Objects.equals(str, filter)) {
                                flag = true;
                                break;
                            }
                        }
                        if(flag){
                            messages2.add(message);
                        }
                    }
                } else {
                    messages2 = messageRepo.findAll();
                }
                for(Message message : messages){
                    message.setAverageRate(rateService.calcAverageRate(message));
                }
                Collections.reverse( messages2);

                break;
            case 2:
                if (filter != null && !filter.isEmpty()) {
                    for(Message message : messages){
                        String[] textOfName = message.getName().split(" ");
                        boolean flag = false;
                        for(String str : textOfName){
                            if (Objects.equals(str, filter)) {
                                flag = true;
                                break;
                            }
                        }
                        if(flag){
                            messages2.add(message);
                        }
                    }
                } else {
                    messages2 = messageRepo.findAll();
                }
                for(Message message : messages){
                    message.setAverageRate(rateService.calcAverageRate(message));
                }
                Collections.reverse( messages2);

                break;
            case 3:
                if (filter != null && !filter.isEmpty()) {
                    for(Message message : messages){
                        List<Comment> comments = commentRepo.findByMessageId(message.getId());
                        for(Comment comment : comments){
                            String[] textOfComment = comment.getText().split(" ");
                            boolean flag = false;
                            for(String str : textOfComment){
                                if (Objects.equals(str, filter)) {
                                    flag = true;
                                    break;
                                }
                            }
                            if(flag){
                                messages2.add(message);
                            }
                        }
                    }
                }
                for(Message message : messages){
                    message.setAverageRate(rateService.calcAverageRate(message));
                }
                Collections.reverse( messages2);

                break;
            case 4:
                if (filter != null && !filter.isEmpty()) {
                    messages2 = messageRepo.findByTag(filter);
                } else {
                    messages2 = messageRepo.findAll();
                }
                for(Message message : messages){
                    message.setAverageRate(rateService.calcAverageRate(message));
                }
                Collections.reverse( messages2);

                break;
            case 5:
                if (filter != null && !filter.isEmpty()) {
                    messages2 = messageRepo.findByHashtag(filter);
                } else {
                    messages2 = messageRepo.findAll();
                }
                for(Message message : messages){
                    message.setAverageRate(rateService.calcAverageRate(message));
                }
                Collections.reverse( messages2);

                break;
            case 6:
                if (filter != null && !filter.isEmpty()) {
                    for(Message message : messages){
                        String[] textOfMessage = message.getText().split(" ");
                        boolean flag = false;
                        for(String str : textOfMessage){
                            if (Objects.equals(str, filter)) {
                                flag = true;
                                break;
                            }
                        }
                        if(flag){
                            messages2.add(message);
                        }
                    }
                } else {
                    messages2 = messageRepo.findAll();
                }
                Collections.reverse(messages2);
                for (Message message : messages) {
                    message.setAverageRate(rateService.calcAverageRate(message));
                }

                break;
            default:
                messages2 = messageRepo.findAll();
                Collections.reverse(messages2);
                for (Message message : messages) {
                    message.setAverageRate(rateService.calcAverageRate(message));
                }
        }

        switch (sortChoice) {
            case 1:
                model.addAttribute("messages", messages2);
                break;
            case 2:
                Collections.reverse(messages2);
                model.addAttribute("messages", messages2);
                break;
        }
        boolean userChoice = true;
        if (user != null) {
            userChoice = Objects.equals(user.getChoice(), "ENG");
        }
        boolean isAdmin = false;
        if (user != null) {
            isAdmin = user.isAdmin();
        }
        boolean theme = true;
        if (user != null) {
            theme = Objects.equals(user.getTheme(), "LIGHT");
            ;
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
                         Model model,
                         @AuthenticationPrincipal User user
    ) {
        Iterable<Message> messages = messageRepo.findAll();
        List<Message> messages1 = messageRepo.findAll();
        messages1.clear();
        for (Message message : messages) {
            message.setLikesCount(message.getLikes().size());
        }
        Integer counter = 0;
        for (Message message : messages) {
            if (Objects.equals(message.getAuthor().getUsername(), user.getUsername())) {
                messages1.add(message);
                counter++;
            }
        }
        for (Message message : messages) {
            message.setMeLiked(0);
        }
        List<User> users = userRepo.findAll();
        for (User user1 : users) {
            Integer postCount = 0;
            Integer likesCount = 0;

            for (Message message : messages) {
                for (User user3 : message.getLikes()) {
                    if (Objects.equals(user3.getUsername(), user.getUsername())) {
                        message.setMeLiked(1);
                    }
                }
                if (Objects.equals(message.getAuthor().getUsername(), user1.getUsername())) {
                    postCount++;
                    likesCount += message.getLikes().size();
                }
            }
            user1.setCountOfLikes(likesCount);
            user1.setCountOfPosts(postCount);
        }

        List<Message> messages2 = messageRepo.findAll();
        messages2.clear();
        switch (choice) {
            case 1:
                if (filter != null && !filter.isEmpty()) {
                    for (Message message1 : messages1) {
                        String[] textOfName = message1.getName().split(" ");
                        boolean flag = false;
                        for (String str : textOfName) {
                            if (Objects.equals(str, filter)) {
                                flag = true;
                                break;
                            }
                        }
                        List<Comment> comments = commentRepo.findByMessageId(message1.getId());
                        for (Comment comment : comments) {
                            String[] textOfComment = comment.getText().split(" ");
                            for(String str : textOfComment){
                                if (Objects.equals(str, filter)) {
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        if (Objects.equals(message1.getTag(), filter)) {
                            flag = true;
                        }
                        if (Objects.equals(message1.getHashtag(), filter)) {
                            flag = true;
                        }
                        String[] textOfMessage = message1.getText().split(" ");
                        for(String str : textOfMessage){
                            if (Objects.equals(str, filter)) {
                                flag = true;
                                break;
                            }
                        }
                        if(flag){
                            messages2.add(message1);
                        }
                    }
                } else {
                    messages2 = messageRepo.findByAuthor(user);
                }
                for (Message message : messages2) {
                    message.setAverageRate(rateService.calcAverageRate(message));
                }
                Collections.reverse( messages2);

                break;
            case 2:
                if (filter != null && !filter.isEmpty()) {
                    for(Message message : messages1){
                        String[] textOfName = message.getName().split(" ");
                        boolean flag = false;
                        for(String str : textOfName){
                            if (Objects.equals(str, filter)) {
                                flag = true;
                                break;
                            }
                        }
                        if(flag){
                            messages2.add(message);
                        }
                    }
                } else {
                    messages2 = messageRepo.findByAuthor(user);
                    ;
                }
                for (Message message : messages1) {
                    message.setAverageRate(rateService.calcAverageRate(message));
                }
                Collections.reverse( messages2);

                break;
            case 3:
                if (filter != null && !filter.isEmpty()) {
                    for(Message message : messages1){
                        List<Comment> comments = commentRepo.findByMessageId(message.getId());
                        for(Comment comment : comments){
                            String[] textOfComment = comment.getText().split(" ");
                            boolean flag = false;
                            for(String str : textOfComment){
                                if (Objects.equals(str, filter)) {
                                    flag = true;
                                    break;
                                }
                            }
                            if (flag) {
                                messages2.add(message);
                            }
                        }
                    }
                } else {
                    messages2 = messageRepo.findByAuthor(user);
                }
                for (Message message : messages1) {
                    message.setAverageRate(rateService.calcAverageRate(message));
                }
                Collections.reverse( messages2);

                break;
            case 4:
                if (filter != null && !filter.isEmpty()) {
                    messages2 = messageRepo.findByTag(filter);
                } else {
                    messages2 = messageRepo.findByAuthor(user);
                }
                for (Message message : messages1) {
                    message.setAverageRate(rateService.calcAverageRate(message));
                }
                Collections.reverse( messages2);

                break;
            case 5:
                if (filter != null && !filter.isEmpty()) {
                    messages2 = messageRepo.findByHashtag(filter);
                } else {
                    messages2 = messageRepo.findByAuthor(user);
                }
                for (Message message : messages1) {
                    message.setAverageRate(rateService.calcAverageRate(message));
                }
                Collections.reverse( messages2);

                break;
            case 6:
                if (filter != null && !filter.isEmpty()) {
                    for(Message message : messages1){
                        String[] textOfMessage = message.getText().split(" ");
                        boolean flag = false;
                        for(String str : textOfMessage){
                            if (Objects.equals(str, filter)) {
                                flag = true;
                                break;
                            }
                        }
                        if(flag){
                            messages2.add(message);
                        }
                    }
                } else {
                    messages2 = messageRepo.findByAuthor(user);
                }
                Collections.reverse(messages2);
                for (Message message : messages) {
                    message.setAverageRate(rateService.calcAverageRate(message));
                }

                break;
            default:
                messages2 = messageRepo.findByAuthor(user);
                Collections.reverse(messages2);
                for (Message message : messages) {
                    message.setAverageRate(rateService.calcAverageRate(message));
                }
        }
        switch (sortChoice) {
            case 1:
                model.addAttribute("messages", messages2);
                break;
            case 2:
                Collections.reverse(messages2);
                model.addAttribute("messages", messages2);
                break;
        }
        boolean userChoice = true;
        if (user != null) {
            userChoice = Objects.equals(user.getChoice(), "ENG");
        }
        boolean isAdmin = false;
        if (user != null) {
            isAdmin = user.isAdmin();
        }
        boolean theme = true;
        if (user != null) {
            theme = Objects.equals(user.getTheme(), "LIGHT");
            ;
        }
        model.addAttribute("userLikes", user.getCountOfLikes());
        model.addAttribute("theme", theme);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("lang", userChoice);
        model.addAttribute("countOfPosts", counter);
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
        Iterable<Message> messages = messageRepo.findAll();
        ArrayList<Message> messages1 = new ArrayList<>();
        User user = userRepo.findByUsername(username);
        Integer counter = 0;
        for (Message message : messages) {
            message.setLikesCount(message.getLikes().size());
        }
        for (Message message : messages) {
            message.setMeLiked(0);
        }
        List<User> users = userRepo.findAll();
        for (User user1 : users) {
            Integer postCount = 0;
            Integer likesCount = 0;
            for (Message message : messages) {
                for (User user3 : message.getLikes()) {
                    if (Objects.equals(user3.getUsername(), user.getUsername())) {
                        message.setMeLiked(1);
                    }
                }
                if (Objects.equals(message.getAuthor().getUsername(), user1.getUsername())) {
                    postCount++;
                    likesCount += message.getLikes().size();
                }
            }
            user1.setCountOfLikes(likesCount);
            user1.setCountOfPosts(postCount);
        }
        boolean admin;
        admin = currentUser.isAdmin();
        for (Message message : messages) {
            if (Objects.equals(message.getAuthor().getUsername(), username)) {
                messages1.add(message);
                counter++;
            }
        }
        List<Message> messages2 = messageRepo.findAll();
        messages2.clear();
        switch (choice){
            case 1:
                if (filter != null && !filter.isEmpty()) {
                    for(Message message : messages1){
                        String[] textOfName = message.getName().split(" ");
                        boolean flag = false;
                        for(String str : textOfName){
                            if (Objects.equals(str, filter)) {
                                flag = true;
                                break;
                            }
                        }
                        List<Comment> comments = commentRepo.findByMessageId(message.getId());
                        for(Comment comment : comments){
                            String[] textOfComment = comment.getText().split(" ");
                            for(String str : textOfComment){
                                if (Objects.equals(str, filter)) {
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        if(Objects.equals(message.getTag(), filter)){
                            flag = true;
                        }
                        if(Objects.equals(message.getHashtag(),filter)){
                            flag = true;
                        }
                        String[] textOfMessage = message.getText().split(" ");
                        for(String str : textOfMessage){
                            if (Objects.equals(str, filter)) {
                                flag = true;
                                break;
                            }
                        }
                        if(flag){
                            messages2.add(message);
                        }
                    }
                } else {
                    messages2 = messageRepo.findByAuthor(user);
                }
                for(Message message : messages){
                    message.setAverageRate(rateService.calcAverageRate(message));
                }
                Collections.reverse( messages2);

                break;
            case 2:
                if (filter != null && !filter.isEmpty()) {
                    for(Message message : messages1){
                        String[] textOfName = message.getName().split(" ");
                        boolean flag = false;
                        for(String str : textOfName){
                            if (Objects.equals(str, filter)) {
                                flag = true;
                                break;
                            }
                        }
                        if(flag){
                            messages2.add(message);
                        }
                    }
                } else {
                    messages2 = messageRepo.findByAuthor(user);
                }
                for(Message message : messages){
                    message.setAverageRate(rateService.calcAverageRate(message));
                }
                Collections.reverse( messages2);

                break;
            case 3:
                if (filter != null && !filter.isEmpty()) {
                    for(Message message : messages1){
                        List<Comment> comments = commentRepo.findByMessageId(message.getId());
                        for(Comment comment : comments){
                            String[] textOfComment = comment.getText().split(" ");
                            boolean flag = false;
                            for(String str : textOfComment){
                                if (Objects.equals(str, filter)) {
                                    flag = true;
                                    break;
                                }
                            }
                            if (flag) {
                                messages2.add(message);
                            }
                        }
                    }
                } else {
                    messages2 = messageRepo.findByAuthor(user);
                }
                for(Message message : messages){
                    message.setAverageRate(rateService.calcAverageRate(message));
                }
                Collections.reverse( messages2);

                break;
            case 4:
                if (filter != null && !filter.isEmpty()) {
                    messages2 = messageRepo.findByTag(filter);
                } else {
                    messages2 = messageRepo.findByAuthor(user);
                }
                for(Message message : messages){
                    message.setAverageRate(rateService.calcAverageRate(message));
                }
                Collections.reverse( messages2);

                break;
            case 5:
                if (filter != null && !filter.isEmpty()) {
                    messages2 = messageRepo.findByHashtag(filter);
                } else {
                    messages2 = messageRepo.findByAuthor(user);
                }
                for(Message message : messages){
                    message.setAverageRate(rateService.calcAverageRate(message));
                }
                Collections.reverse( messages2);

                break;
            case 6:
                if (filter != null && !filter.isEmpty()) {
                    for(Message message : messages1){
                        String[] textOfMessage = message.getText().split(" ");
                        boolean flag = false;
                        for(String str : textOfMessage){
                            if (Objects.equals(str, filter)) {
                                flag = true;
                                break;
                            }
                        }
                        if(flag){
                            messages2.add(message);
                        }
                    }
                } else {
                    messages2 = messageRepo.findByAuthor(user);
                }
                Collections.reverse(messages2);
                for (Message message : messages) {
                    message.setAverageRate(rateService.calcAverageRate(message));
                }

                break;
            default:
                messages2 = messageRepo.findByAuthor(user);
                Collections.reverse(messages2);
                for (Message message : messages) {
                    message.setAverageRate(rateService.calcAverageRate(message));
                }

        }
        switch (sortChoice) {
            case 1:
                model.addAttribute("messages", messages2);
                break;
            case 2:
                Collections.reverse(messages2);
                model.addAttribute("messages", messages2);
                break;
        }
        boolean userChoice = true;
        if (user != null) {
            userChoice = Objects.equals(user.getChoice(), "ENG");
        }
        boolean isAdmin = false;
        if (user != null) {
            isAdmin = user.isAdmin();
        }
        boolean theme = true;
        if (user != null) {
            theme = Objects.equals(user.getTheme(), "LIGHT");
        }
        boolean isCurrentUser = Objects.equals(user.getUsername(), currentUser.getUsername());
        boolean isSubscriber = false;
        for (User user2 : user.getSubscribers()) {
            if (Objects.equals(user2.getUsername(), currentUser.getUsername())) {
                isSubscriber = true;
            }
        }
        model.addAttribute("isSubscriber", isSubscriber);
        model.addAttribute("isCurrentUser", isCurrentUser);
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("userLikes", user.getCountOfLikes());
        model.addAttribute("theme", theme);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("lang", userChoice);
        model.addAttribute("countOfPosts", counter);
        model.addAttribute("admin", admin);
        model.addAttribute("user", user);
        model.addAttribute("filter", filter);

        return "userProfile";
    }

    public static String convertMarkdownToHTML(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();
        return htmlRenderer.render(document);
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
        User user = userRepo.findByUsername(username);
        Message message = new Message(text, tag, name, hashtag, user);

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File temp = null;
            try {
                temp = File.createTempFile("myTempFile", ".png");
            } catch (IOException e) {
                e.printStackTrace();
            }

            file.transferTo(temp);
            Map uploadResult = cloudinary.uploader().upload(temp, ObjectUtils.emptyMap());
            String resultFilename = (String) uploadResult.get("url");
            message.setFilename(resultFilename);
        }

        String htmlValue = convertMarkdownToHTML(message.getText());

        message.setText(htmlValue);
        messageRepo.save(message);

        Iterable<Message> messages = messageRepo.findAll();

        model.put("messages", messages);

        return "redirect:/user/profile";
    }

    @GetMapping("/post/{id}")
    public String userPost(@PathVariable Integer id, @AuthenticationPrincipal User user, Model model) {
        List<Message> messages = (List<Message>) messageRepo.findAll();
        Message message = messageRepo.findById(id);
        List<Comment> comments = commentRepo.findByMessageId(id);
        Collections.reverse(comments);
        for (Message message1 : messages) {
            message1.setAverageRate(rateService.calcAverageRate(message1));
            message.setLikesCount(message.getLikes().size());
        }
        for (Message message12 : messages) {
            message12.setMeLiked(0);
        }
        List<User> users = userRepo.findAll();
        for (User user1 : users) {
            Integer postCount = 0;
            Integer likesCount = 0;
            for (Message message1 : messages) {
                for (User user3 : message.getLikes()) {
                    if (Objects.equals(user3.getUsername(), user.getUsername())) {
                        message.setMeLiked(1);
                    }
                }
                if (Objects.equals(message1.getAuthor().getUsername(), user1.getUsername())) {
                    postCount++;
                    likesCount += message1.getLikes().size();
                }
            }
            user1.setCountOfLikes(likesCount);
            user1.setCountOfPosts(postCount);
        }
        boolean userChoice = Objects.equals(user.getChoice(), "ENG");
        boolean isAdmin = user.isAdmin();
        boolean theme = Objects.equals(user.getTheme(), "LIGHT");
        model.addAttribute("theme", theme);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("lang", userChoice);
        model.addAttribute("user", user);
        model.addAttribute("comments", comments);
        model.addAttribute("message", message);
        return "post";
    }

    @PostMapping("/user/profile/update/{id}")
    public String postUpdate(@AuthenticationPrincipal User user,
                             @PathVariable Integer id,
                             @RequestParam String text,
                             @RequestParam String name,
                             @RequestParam String hashtag,
                             @RequestParam String tag, Map<String, Object> model,
                             @RequestParam("file") MultipartFile file) throws IOException {

        Message message = messageRepo.findById(id);
        String messageHashtag = message.getHashtag();
        String messageText = message.getText();
        String messageName = message.getName();
        String messageTopic = message.getTag();
        boolean isTextChange = (text != null && !text.equals(messageText)) ||
                (messageText != null && !messageText.equals(text));
        if(isTextChange) {
            String htmlValue = convertMarkdownToHTML(text);
            message.setText(htmlValue);
        }

        boolean isHashtagChange = (hashtag != null && !hashtag.equals(messageHashtag)) ||
                (messageHashtag != null && !messageHashtag.equals(hashtag));
        if(isHashtagChange){
            message.setHashtag(hashtag);
        }

        boolean isNameChanged = (name != null && !name.equals(messageName)) ||
                (messageName != null && !messageName.equals(name));
        if (isNameChanged) {
            message.setName(name);
        }
        boolean isTopicChanged = (tag != null && !tag.equals(messageTopic)) ||
                (messageTopic != null && !messageTopic.equals(tag));
        if (isTopicChanged) {
            message.setTag(tag);
        }
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File temp = null;
            try {
                temp = File.createTempFile("myTempFile", ".png");
            } catch (IOException e) {
                e.printStackTrace();
            }
            file.transferTo(temp);
            Map uploadResult = cloudinary.uploader().upload(temp, ObjectUtils.emptyMap());
            String resultFilename = (String) uploadResult.get("url");
            message.setFilename(resultFilename);
        }
        messageRepo.save(message);
        return "redirect:/user/profile";
    }

    @GetMapping("/user/profile/update/{id}")
    public String postUpdateForm(@PathVariable Integer id, Model model, @AuthenticationPrincipal User user) {
        Message message = messageRepo.findById(id);

        boolean userChoice = Objects.equals(user.getChoice(), "ENG");
        boolean theme = Objects.equals(user.getTheme(), "LIGHT");
        model.addAttribute("theme", theme);
        model.addAttribute("lang", userChoice);
        model.addAttribute("message", message);
        return "editMess";
    }

    @PostMapping("/user/profile/update/{id}/delete")
    public String deletePost(@PathVariable Integer id) {
        Message message = messageRepo.findById(id);
        messageRepo.delete(message);
        return "redirect:/user/profile";
    }

    @GetMapping("/post/hashtag/{hashtag}")
    public String allByHashtag(@PathVariable String hashtag, Model model, @AuthenticationPrincipal User user) {
        List<Message> messages = messageRepo.findAll();
        messages = messageRepo.findByHashtag(hashtag);
        Collections.reverse(messages);
        for (Message message : messages) {
            message.setMeLiked(0);
        }
        for (Message message : messages) {
            for (User user3 : message.getLikes()) {
                if (Objects.equals(user3.getUsername(), user.getUsername())) {
                    message.setMeLiked(1);
                }
            }
            message.setAverageRate(rateService.calcAverageRate(message));
            message.setLikesCount(message.getLikes().size());
        }
        List<User> users = userRepo.findAll();
        for (User user1 : users) {
            Integer postCount = 0;
            Integer likesCount = 0;

            for (Message message1 : messages) {
                if (Objects.equals(message1.getAuthor().getUsername(), user1.getUsername())) {
                    postCount++;
                    likesCount += message1.getLikes().size();
                }
            }
            user1.setCountOfLikes(likesCount);
            user1.setCountOfPosts(postCount);
        }
        boolean userChoice = Objects.equals(user.getChoice(), "ENG");
        boolean isAdmin = user.isAdmin();
        boolean theme = Objects.equals(user.getTheme(), "LIGHT");
        model.addAttribute("user", user);
        model.addAttribute("theme", theme);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("lang", userChoice);
        model.addAttribute("messages", messages);
        model.addAttribute("hashtag", hashtag);

        return "allByTag";
    }

    @GetMapping("/post/topic/{topic}")
    public String allByTopic(@PathVariable String topic, Model model, @AuthenticationPrincipal User user) {
        List<Message> messages = messageRepo.findAll();
        messages = messageRepo.findByTag(topic);
        Collections.reverse(messages);
        for (Message message : messages) {
            message.setMeLiked(0);
        }
        for (Message message : messages) {
            for (User user3 : message.getLikes()) {
                if (Objects.equals(user3.getUsername(), user.getUsername())) {
                    message.setMeLiked(1);
                }
            }
            message.setAverageRate(rateService.calcAverageRate(message));
            message.setLikesCount(message.getLikes().size());
        }
        List<User> users = userRepo.findAll();
        for (User user1 : users) {
            Integer postCount = 0;
            Integer likesCount = 0;

            for (Message message1 : messages) {
                if (Objects.equals(message1.getAuthor().getUsername(), user1.getUsername())) {
                    postCount++;
                    likesCount += message1.getLikes().size();
                }
            }
            user1.setCountOfLikes(likesCount);
            user1.setCountOfPosts(postCount);
        }
        boolean isAdmin = user.isAdmin();
        boolean userChoice = Objects.equals(user.getChoice(), "ENG");
        boolean theme = Objects.equals(user.getTheme(), "LIGHT");
        model.addAttribute("user", user);
        model.addAttribute("theme", theme);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("lang", userChoice);
        model.addAttribute("messages", messages);
        model.addAttribute("topic", topic);
        return "byTopic";
    }
}

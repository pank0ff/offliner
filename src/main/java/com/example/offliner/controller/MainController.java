package com.example.offliner.controller;

import com.example.offliner.domain.Comment;
import com.example.offliner.domain.Message;
import com.example.offliner.domain.User;
import com.example.offliner.repos.CommentRepo;
import com.example.offliner.repos.MessageRepo;
import com.example.offliner.repos.RateRepo;
import com.example.offliner.repos.UserRepo;
import com.example.offliner.service.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting(Model model) {
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
        List<Message> messages2 = messageRepo.findAll();
        messages2.clear();

        switch (choice){
            case 1:
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
        Integer counter = 0;
        for (Message message : messages) {
            if (Objects.equals(message.getAuthor().getUsername(), user.getUsername())) {
                messages1.add(message);
                counter++;
            }
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
                                          @AuthenticationPrincipal User currentUser){
        Iterable<Message> messages = messageRepo.findAll();
        ArrayList<Message> messages1 = new ArrayList<>();
        User user = userRepo.findByUsername(username);
        Integer counter = 0;
        boolean admin;
        admin = currentUser.isAdmin();
        for(Message message:messages){
            if(Objects.equals(message.getAuthor().getUsername(), username)){
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

        model.addAttribute("countOfPosts", counter);
        model.addAttribute("admin", admin);
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
        User user = userRepo.findByUsername(username);
        Message message = new Message(text, tag,name,hashtag, user);

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            message.setFilename(resultFilename);
        }

        messageRepo.save(message);

        Iterable<Message> messages = messageRepo.findAll();

        model.put("messages", messages);

        return "redirect:/user/profile";
    }
    @GetMapping("/post/{id}")
    public String userEditForm(@PathVariable Integer id,@AuthenticationPrincipal User user, Model model) {
        List<Message> messages = (List<Message>) messageRepo.findAll();
        Message message = messageRepo.findById(id);
        List<Comment> comments = commentRepo.findByMessageId(id);
        Collections.reverse(comments);
        for(Message message1 : messages){
            message1.setAverageRate(rateService.calcAverageRate(message1));
        }
        model.addAttribute("user",user);
        model.addAttribute("comments",comments);
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
        if(isTextChange){
            message.setText(text);
        }

        boolean isHashtagChange = (hashtag != null && !hashtag.equals(messageHashtag)) ||
                (messageHashtag != null && !messageHashtag.equals(hashtag));
        if(isHashtagChange){
            message.setHashtag(hashtag);
        }

        boolean isNameChanged = (name != null && !name.equals(messageName)) ||
                (messageName != null && !messageName.equals(name));
        if(isNameChanged){
            message.setName(name);
        }
        boolean isTopicChanged = (tag != null && !tag.equals(messageTopic)) ||
                (messageTopic != null && !messageTopic.equals(tag));
        if(isTopicChanged){
            message.setTag(tag);
        }
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            message.setFilename(resultFilename);
        }
        messageRepo.save(message);
        return "redirect:/user/profile";
    }

    @GetMapping("/user/profile/update/{id}")
    public String postUpdateForm(@PathVariable Integer id, Model model) {
        Message message = messageRepo.findById(id);


        model.addAttribute("message", message);
        return "editMess";
    }
    @PostMapping("/user/profile/update/{id}/delete")
    public String deletePost(@PathVariable Integer id){
        Message message = messageRepo.findById(id);
        messageRepo.delete(message);
        return "redirect:/user/profile";
    }

    @GetMapping("/post/hashtag/{hashtag}")
    public String allByHashtag(@PathVariable String hashtag,Model model){
        List<Message> messages = messageRepo.findAll();
        messages = messageRepo.findByHashtag(hashtag);
        Collections.reverse(messages);
        for(Message message : messages){
            message.setAverageRate(rateService.calcAverageRate(message));
        }
        model.addAttribute("messages",messages);
        model.addAttribute("hashtag",hashtag);

        return "allByTag";
    }

    @GetMapping("/post/topic/{topic}")
    public String allByTopic(@PathVariable String topic,Model model){
        List<Message> messages = messageRepo.findAll();
        messages = messageRepo.findByTag(topic);
        Collections.reverse(messages);
        for(Message message : messages){
            message.setAverageRate(rateService.calcAverageRate(message));
        }
        model.addAttribute("messages",messages);
        model.addAttribute("topic",topic);
        return "byTopic";
    }
}

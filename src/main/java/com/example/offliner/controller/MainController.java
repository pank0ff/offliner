package com.example.offliner.controller;

import com.example.offliner.domain.Message;
import com.example.offliner.domain.Role;
import com.example.offliner.domain.User;
import com.example.offliner.repos.MessageRepo;
import com.example.offliner.repos.UserRepo;
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
    private UserRepo userRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting(Model model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Message> messages = messageRepo.findAll();

        if (filter != null && !filter.isEmpty()) {
            messages = messageRepo.findByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }

        Collections.reverse((List<Message>) messages);

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);

        return "main";
    }
    @GetMapping("/user/profile/{username}")
    public String filter(@RequestParam(required = false, defaultValue = "") String filter, Model model,@PathVariable String username){
        Iterable<Message> messages = messageRepo.findAll();
        ArrayList<Message> messages1 = new ArrayList<Message>();
        User user = userRepo.findByUsername(username);
        for(Message message:messages){
            if(Objects.equals(message.getAuthor().getUsername(), user.getUsername())){
                messages1.add(message);
            }
        }
        ArrayList<Message> messages2 = new ArrayList<Message>();
        if (filter != null && !filter.isEmpty()) {
            for(Message message : messages1){
                if(Objects.equals(message.getTag(), filter)){
                    messages2.add(message);
                }
            }
        } else {
            messages2 = messages1;
        }
        Collections.reverse(messages2);
        model.addAttribute("user",user);
        model.addAttribute("messages", messages2);
        model.addAttribute("filter", filter);

        return "profile";
    }

    @PostMapping("/user/profile/add/{id}")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String name,
            @RequestParam String tag, Map<String, Object> model,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        Message message = new Message(text, tag,name, user);

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

        return "redirect:/main";
    }
    @GetMapping("/post/{id}")
    public String userEditForm(@PathVariable Integer id, Model model) {
        Message message = messageRepo.findById(id);
        model.addAttribute("message", message);
        return "post";
    }
    @PostMapping("/user/profile/update/{id}")
    public String postUpdate(@AuthenticationPrincipal User user,
                             @PathVariable Integer id,
                             @RequestParam String text,
                             @RequestParam String name,
                             @RequestParam String tag, Map<String, Object> model,
                             @RequestParam("file") MultipartFile file) throws IOException {

        Message message = messageRepo.findById(id);
        String messageText = message.getText();
        String messageName = message.getName();
        String messageTopic = message.getTag();
        boolean isTextChange = (text != null && !text.equals(messageText)) ||
                (messageText != null && !messageText.equals(text));
        if(isTextChange){
            message.setText(text);
        }
        boolean isNameChanged = (name != null && !name.equals(messageName)) ||
                (messageName != null && !messageName.equals(name));
        if(isNameChanged){
            message.setName(name);
        }
        boolean isTopicChanged = (tag != null && !tag.equals(messageTopic)) ||
                (messageTopic != null && !messageTopic.equals(tag));
        if(isTopicChanged){
            message.setName(tag);
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
        return "redirect:/main";
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
        return "redirect:/main";
    }

}

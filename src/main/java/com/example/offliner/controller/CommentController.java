package com.example.offliner.controller;

import com.example.offliner.domain.Message;
import com.example.offliner.domain.User;
import com.example.offliner.service.CommentService;
import com.example.offliner.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CommentController {
    private final CommentService commentService;
    private final MessageService messageService;

    @Autowired
    public CommentController(CommentService commentService, MessageService messageService) {
        this.commentService = commentService;
        this.messageService = messageService;
    }

    @PostMapping("/post/add/comment/{id}")
    public String create(
            @PathVariable Integer id,
            @RequestParam String text,
            @AuthenticationPrincipal User user
    ){
        Message message = messageService.getMessageById(id);
       commentService.create(message,text,user);
       return "redirect:/main";
    }
}

package com.example.offliner.controller;

import com.example.offliner.domain.Comment;
import com.example.offliner.domain.Message;
import com.example.offliner.domain.User;
import com.example.offliner.repos.MessageRepo;
import com.example.offliner.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @Autowired
    private MessageRepo messageRepo;

    @PostMapping("/post/add/comment/{id}")
    public String create(
            @PathVariable Integer id,
            @RequestParam String text,
            @AuthenticationPrincipal User user
    ){
        Message message = messageRepo.findById(id);
       commentService.create(message,text,user);
       return "redirect:/main";
    }
}

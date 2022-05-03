package com.example.offliner.controller;

import com.example.offliner.domain.User;
import com.example.offliner.exception.ApiRequestException;
import com.example.offliner.service.CommentService;
import com.example.offliner.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Validated
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
            @Valid @RequestParam String text,
            @AuthenticationPrincipal User user
    ) {
        try {
            commentService.create(messageService.getMessageById(id), text, user);
        } catch (Exception e) {
            throw new ApiRequestException("can't create comment");
        }
        return "redirect:/main";
    }
}

package com.example.offliner.service;

import com.example.offliner.domain.Comment;
import com.example.offliner.domain.Message;
import com.example.offliner.domain.User;
import com.example.offliner.repos.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepo commentRepo;

    @Autowired
    public CommentService(CommentRepo commentRepo){
        this.commentRepo = commentRepo;
    }

    public Comment create(Message message, String text, User user){
        Comment comment = new Comment();
        comment.setMessage(message);
        comment.setText(text);
        comment.setAuthor(user);
        commentRepo.save(comment);
        return comment;
    }

}

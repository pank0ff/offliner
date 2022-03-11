package com.example.offliner.repos;

import com.example.offliner.domain.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment,Long> {
   @EntityGraph(attributePaths = {"comments"})
   List<Comment> findAll();

    List<Comment> findByMessageId(Integer id);
}

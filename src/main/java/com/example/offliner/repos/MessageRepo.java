package com.example.offliner.repos;

import com.example.offliner.domain.Message;
import com.example.offliner.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepo extends CrudRepository<Message, Long> {

    List<Message> findAll();
    List<Message> findByTag(String tag);

    List<Message> findByName(String filter);

    Message findById(Integer id);

    List<Message> findByAuthor(User user);

    List<Message> findByHashtag(String hashtag);
}

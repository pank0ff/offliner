package com.example.offliner.domain;

import com.example.offliner.domain.Message;
import com.example.offliner.domain.User;
import com.example.offliner.repos.RateRepo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
public class Rate {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int rate;

    public Rate(Message message,User user,int rate){
        this.message =message;
        this.user = user;
        this.rate = rate;
    }

    public Rate() {

    }

    public User getUser() {
        return user;
    }

    public Message getMessage() {
        return message;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }



}

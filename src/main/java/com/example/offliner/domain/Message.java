package com.example.offliner.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Lob
    private String text;
    private String tag;
    private String name;
    private String hashtag;
    private double averageRate;

    @OneToMany(mappedBy = "message",orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "message", orphanRemoval = true)
    private List<Rate> rates;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    private String filename;

    public Message(List<Comment> comments) {
        this.comments = comments;
    }

    public Message(String text, String tag, String name, String hashtag, User user) {
        this.author = user;
        this.text = text;
        this.tag = tag;
        this.hashtag = hashtag;
        this.name = name;
        this.averageRate = 0;
    }

    public Message() {
    }

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public double getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(double averageRate) {
        this.averageRate = averageRate;
    }

}

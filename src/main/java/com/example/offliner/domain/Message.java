package com.example.offliner.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
    @Size(min = 5)
    private String text;

    @NotBlank
    @Size(min = 2, max = 7)
    private String tag;

    @NotBlank
    @Size(min = 5)
    private String name;

    @NotBlank
    @Size(min = 1)
    private String hashtag;

    @Min(1)
    @Max(5)
    private double averageRate;

    @Min(0)
    private int likesCount;

    @Min(0)
    @Max(1)
    private int meLiked;

    @OneToMany(mappedBy = "message", orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "message", orphanRemoval = true)
    private List<Rate> rates;

    @ManyToMany
    @JoinTable(
            name = "message_likes",
            joinColumns = {@JoinColumn(name = "message_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private final Set<User> likes = new HashSet<>();

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
        this.likesCount = 0;
        this.meLiked = 0;
    }

    public Message() {
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public User getAuthor() {
        return author;
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

    public Set<User> getLikes() {
        return likes;
    }

    public int getMeLiked() {
        return meLiked;
    }

    public void setMeLiked(int meLiked) {
        this.meLiked = meLiked;
    }
}

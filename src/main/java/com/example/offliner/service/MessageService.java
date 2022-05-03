package com.example.offliner.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.offliner.domain.Comment;
import com.example.offliner.domain.Message;
import com.example.offliner.domain.User;
import com.example.offliner.repos.CommentRepo;
import com.example.offliner.repos.MessageRepo;
import com.example.offliner.repos.UserRepo;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class MessageService {
    private final CommentRepo commentRepo;
    private final RateService rateService;
    private final MessageRepo messageRepo;
    private final UserRepo userRepo;
    private final UserService userService;
    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dwnzejl4h",
            "api_key", "424976915584458",
            "api_secret", "TlQsPJt2OHBBSJVzwe31u3zFqgY"));

    @Autowired
    public MessageService(CommentRepo commentRepo, RateService rateService, MessageRepo messageRepo, UserRepo userRepo, UserService userService) {
        this.commentRepo = commentRepo;
        this.userService = userService;
        this.userRepo = userRepo;
        this.rateService = rateService;
        this.messageRepo = messageRepo;
    }

    public static String convertMarkdownToHTML(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer htmlRenderer = HtmlRenderer.builder().build();
        return htmlRenderer.render(document);
    }

    public List<Message> sortMessages(int choice, String filter, List<Message> messages) {
        ArrayList<Message> messages2 = new ArrayList<>();
        switch (choice) {
            case 1:
                if (filter != null && !filter.isEmpty()) {
                    for (Message message : messages) {
                        if (message.getName().contains(filter)) {
                            messages2.add(message);
                        } else {
                            if (message.getText().contains(filter)) {
                                messages2.add(message);
                            } else {
                                if (message.getTag().contains(filter)) {
                                    messages2.add(message);
                                } else {
                                    if (message.getHashtag().contains(filter)) {
                                        messages2.add(message);
                                    } else {
                                        for (Comment comment : commentRepo.findByMessageId(message.getId())) {
                                            if (comment.getText().contains(filter)) {
                                                messages2.add(message);
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    messages2 = (ArrayList<Message>) messages;
                }
                setMessagesAverageRate(messages2);
                Collections.reverse(messages2);

                break;
            case 2:
                if (filter != null && !filter.isEmpty()) {
                    for (Message message : messages) {
                        if (message.getName().contains(filter)) {
                            messages2.add(message);
                        }
                    }
                } else {
                    messages2 = (ArrayList<Message>) messages;
                }
                setMessagesAverageRate(messages2);
                Collections.reverse(messages2);

                break;
            case 3:
                if (filter != null && !filter.isEmpty()) {
                    for (Message message : messages) {
                        for (Comment comment : commentRepo.findByMessageId(message.getId())) {
                            if (comment.getText().contains(filter)) {
                                messages2.add(message);
                                break;
                            }
                        }
                    }
                }
                setMessagesAverageRate(messages2);
                Collections.reverse(messages2);

                break;
            case 4:
                if (filter != null && !filter.isEmpty()) {
                    for (Message message : messages) {
                        if (message.getTag().contains(filter)) {
                            messages2.add(message);
                        }
                    }
                } else {
                    messages2 = (ArrayList<Message>) messages;
                }
                setMessagesAverageRate(messages2);
                Collections.reverse(messages2);

                break;
            case 5:
                if (filter != null && !filter.isEmpty()) {
                    for (Message message : messages) {
                        if (message.getHashtag().contains(filter)) {
                            messages2.add(message);
                        }
                    }
                } else {
                    messages2 = (ArrayList<Message>) messages;
                }
                setMessagesAverageRate(messages2);
                Collections.reverse(messages2);

                break;
            case 6:
                if (filter != null && !filter.isEmpty()) {
                    for (Message message : messages) {
                        if (message.getText().contains(filter)) {
                            messages2.add(message);
                        }
                    }
                } else {
                    messages2 = (ArrayList<Message>) messages;
                }
                Collections.reverse(messages2);
                setMessagesAverageRate(messages2);

                break;
            default:
                messages2 = (ArrayList<Message>) messages;
                Collections.reverse(messages2);
                setMessagesAverageRate(messages2);
        }
        return messages2;
    }

    public void postUpdate(Integer id, String text, String hashtag, String tag, String name, MultipartFile file) throws IOException {
        Message message = messageRepo.findById(id);
        String messageHashtag = message.getHashtag();
        String messageText = message.getText();
        String messageName = message.getName();
        String messageTopic = message.getTag();
        boolean isTextChange = (text != null && !text.equals(messageText)) ||
                (messageText != null && !messageText.equals(text));
        if (isTextChange) {
            String htmlValue = convertMarkdownToHTML(text);
            message.setText(htmlValue);
        }

        boolean isHashtagChange = (hashtag != null && !hashtag.equals(messageHashtag)) ||
                (messageHashtag != null && !messageHashtag.equals(hashtag));
        if (isHashtagChange) {
            message.setHashtag(hashtag);
        }

        boolean isNameChanged = (name != null && !name.equals(messageName)) ||
                (messageName != null && !messageName.equals(name));
        if (isNameChanged) {
            message.setName(name);
        }
        boolean isTopicChanged = (tag != null && !tag.equals(messageTopic)) ||
                (messageTopic != null && !messageTopic.equals(tag));
        if (isTopicChanged) {
            message.setTag(tag);
        }
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            File temp = null;
            try {
                temp = File.createTempFile("myTempFile", ".png");
            } catch (IOException e) {
                e.printStackTrace();
            }
            file.transferTo(temp);
            Map uploadResult = cloudinary.uploader().upload(temp, ObjectUtils.emptyMap());
            String resultFilename = (String) uploadResult.get("url");
            message.setFilename(resultFilename);
        }
        messageRepo.save(message);
    }

    public void loadMessages(List<Message> messages, User user) {
        Collections.reverse(messages);
        if (user != null) {
            setMeLiked(messages, userRepo.findByUsername(user.getUsername()));
        }
        setMessagesAverageRate(messages);
        setMessagesLikesCount(messages);
        List<User> users = userRepo.findAll();
        for (User user1 : users) {
            user1.setCountOfLikes(userService.getUserLikesCount(user1));
            user1.setCountOfPosts(userService.getUserCountOfPosts(user1));
        }
    }

    public void getPost(Message message, User user) {
        message.setAverageRate(rateService.calcAverageRate(message));
        message.setLikesCount(message.getLikes().size());
        if (message.getLikes().contains(userRepo.findByUsername(user.getUsername()))) {
            message.setMeLiked(1);
        }
        message.getAuthor().setCountOfLikes(userService.getUserLikesCount(message.getAuthor()));
        message.getAuthor().setCountOfPosts(userService.getUserCountOfPosts(message.getAuthor()));
    }

    public void loadToCloudinary(MultipartFile file, Message message) throws IOException {
        File temp = null;
        try {
            temp = File.createTempFile("myTempFile", ".png");
        } catch (IOException e) {
            e.printStackTrace();
        }

        file.transferTo(temp);
        Map uploadResult = cloudinary.uploader().upload(temp, ObjectUtils.emptyMap());
        String resultFilename = (String) uploadResult.get("url");
        message.setFilename(resultFilename);
    }

    public void addMessage(String username, String text, String name, String hashtag, String tag, MultipartFile file) throws IOException {
        User user = userRepo.findByUsername(username);
        Message message = new Message(text, tag, name, hashtag, user);
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            loadToCloudinary(file, message);
        }
        String htmlValue = MessageService.convertMarkdownToHTML(message.getText());
        message.setText(htmlValue);
        messageRepo.save(message);
    }

    public Message getMessageById(Integer id) {
        return messageRepo.findById(id);
    }

    public List<Message> getMessagesByHashtag(String hashtag) {
        return messageRepo.findByHashtag(hashtag);
    }

    public List<Message> getMessagesByTopic(String topic) {
        return messageRepo.findByTag(topic);
    }

    public List<Message> getAllMessages() {
        return messageRepo.findAll();
    }

    public void deleteMessage(Integer id) {
        messageRepo.delete(messageRepo.findById(id));
    }

    public void setMessagesLikesCount(List<Message> messages) {
        for (Message message : messages) {
            message.setLikesCount(message.getLikes().size());
        }
    }

    public List<Message> getMessagesByAuthor(User user) {
        return messageRepo.findByAuthor(user);
    }

    public void setMeLiked(List<Message> messages, User user) {
        for (Message message : messages) {
            if (message.getLikes().contains(user)) {
                message.setMeLiked(1);
            }
        }
    }

    public void setMessagesAverageRate(List<Message> messages) {
        for (Message message : messages) {
            message.setAverageRate(rateService.calcAverageRate(message));
        }
    }
}

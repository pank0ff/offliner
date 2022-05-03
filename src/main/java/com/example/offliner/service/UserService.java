package com.example.offliner.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.offliner.domain.Message;
import com.example.offliner.domain.Role;
import com.example.offliner.domain.User;
import com.example.offliner.repos.MessageRepo;
import com.example.offliner.repos.UserRepo;
import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MessageRepo messageRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }


    public void updateProfile(User user, String password, String email, String aboutYourself, String userChoice, String theme, String linkFacebook, String linkGoogle, String linkYoutube, String linkDribble, String linkLinkedIn, MultipartFile file) throws IOException {
        String userEmail = user.getEmail();
        String choice = user.getChoice();
        String userTheme = user.getTheme();
        String aboutYourself1 = user.getAboutMyself();
        String linkFacebook1 = user.getLinkFacebook();
        String linkGoogle1 = user.getLinkGoogle();
        String linkYoutube1 = user.getLinkYoutube();
        String linkDribble1 = user.getLinkDribble();
        String linkLinkedIn1 = user.getLinkLinkedIn();
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dwnzejl4h",
                "api_key", "424976915584458",
                "api_secret", "TlQsPJt2OHBBSJVzwe31u3zFqgY"));

        boolean isAboutMyselfChanged = (aboutYourself != null && !aboutYourself.equals(aboutYourself1)) ||
                (aboutYourself1 != null && !aboutYourself1.equals(aboutYourself));
        if (isAboutMyselfChanged) {
            user.setAboutMyself(aboutYourself);
        }
        boolean isThemeChanged = (theme != null && !theme.equals(userTheme)) ||
                (userTheme != null && !userTheme.equals(theme));
        if (isThemeChanged) {
            user.setTheme(theme);
        }
        boolean isUserChoiceChanged = (userChoice != null && !userChoice.equals(choice)) ||
                (choice != null && !choice.equals(userChoice));
        if (isUserChoiceChanged) {
            user.setChoice(userChoice);
        }
        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));
        if (isEmailChanged) {
            user.setEmail(email);
        }
        boolean isLinkFacebookChanged = (linkFacebook != null && !linkFacebook.equals(linkFacebook1)) ||
                (linkFacebook1 != null && !linkFacebook1.equals(linkFacebook));
        if (isLinkFacebookChanged) {
            user.setLinkFacebook(linkFacebook);
        }
        boolean isLinkGoogleChanged = (linkGoogle != null && !linkGoogle.equals(linkGoogle1)) ||
                (linkGoogle1 != null && !linkGoogle1.equals(linkGoogle));
        if (isLinkGoogleChanged) {
            user.setLinkGoogle(linkGoogle);
        }
        boolean isLinkYoutubeChanged = (linkYoutube != null && !linkYoutube.equals(linkYoutube1)) ||
                (linkYoutube1 != null && !linkYoutube1.equals(linkYoutube));
        if (isLinkYoutubeChanged) {
            user.setLinkYoutube(linkYoutube);
        }
        boolean isLinkDribbleChanged = (linkDribble != null && !linkDribble.equals(linkDribble1)) ||
                (linkDribble1 != null && !linkDribble1.equals(linkDribble));
        if (isLinkDribbleChanged) {
            user.setLinkDribble(linkDribble);
        }
        boolean isLinkLinkedInChanged = (linkLinkedIn != null && !linkLinkedIn.equals(linkLinkedIn1)) ||
                (linkLinkedIn1 != null && !linkLinkedIn1.equals(linkLinkedIn));
        if (isLinkLinkedInChanged) {
            user.setLinkLinkedIn(linkLinkedIn);
        }
        if (!StringUtils.isEmpty(password)) {
            user.setPassword(password);
        }
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            File temp = null;
            try {
                temp = File.createTempFile("myTempFile", ".png");
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert temp != null;
            file.transferTo(temp);
            Map uploadResult = cloudinary.uploader().upload(temp, ObjectUtils.emptyMap());
            String resultFilename = (String) uploadResult.get("url");
            user.setAvatarFilename(resultFilename);
        }
        userRepo.save(user);
    }


    public void subscribe(User currentUser, User user) {
        user.getSubscribers().add(currentUser);
        userRepo.save(user);
    }

    public void unsubscribe(User currentUser, User user) {
        user.getSubscribers().removeIf(user1 -> Objects.equals(user1.getUsername(), currentUser.getUsername()));
        userRepo.save(user);
    }

    public void like(User user, Message message) {
        message.getLikes().add(user);
        messageRepo.save(message);
    }

    public void unlike(User user, Message message) {
        message.getLikes().removeIf(user1 -> Objects.equals(user1.getUsername(), user.getUsername()));
        messageRepo.save(message);
    }

    public void userSave(String username, Map<String, String> form, User user) {
        user.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepo.save(user);
    }

    public int getUserLikesCount(User user) {
        int counter = 0;
        List<Message> messages = messageRepo.findByAuthor(user);
        for (Message message : messages) {
            counter += message.getLikesCount();
        }
        return counter;
    }

    public int getUserCountOfPosts(User user) {
        return messageRepo.findByAuthor(user).size();
    }

    public boolean isSubscriber(User user, User currentUser) {
        boolean isSubscriber = false;
        for (User user2 : user.getSubscribers()) {
            if (Objects.equals(user2.getUsername(), currentUser.getUsername())) {
                isSubscriber = true;
                break;
            }
        }
        return isSubscriber;
    }

    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public User getUserById(Long id) {
        if (userRepo.findById(id).isPresent()) {
            return userRepo.findById(id).get();
        } else {
            return null;
        }
    }

    public void addUser(User user, String theme, String choice) {
        Date date = new Date();
        user.setDateOfRegistration(date.toString().substring(4));
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setTheme(theme);
        user.setChoice(choice);
        userRepo.save(user);
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public void deleteUser(User user) {
        userRepo.delete(user);
    }

    public double calcUserRate(User user) {
        if (user.getCountOfPosts() == 0) {
            return 0;
        } else {
            double countOfPosts = user.getCountOfPosts();
            double countOfLikes = user.getCountOfLikes();
            return DoubleRounder.round(countOfLikes / countOfPosts, 2);
        }
    }

    public void calcUserRateForAll() {
        for (User user : getAllUsers()) {
            user.setUserRate(calcUserRate(user));
        }
    }

    public boolean getUserTheme(User user) {
        if (user == null) {
            return true;
        } else {
            return Objects.equals(user.getTheme(), "LIGHT");
        }
    }

    public boolean getUserLang(User user) {
        if (user == null) {
            return true;
        } else {
            return Objects.equals(user.getChoice(), "ENG");
        }
    }

    public boolean getUserIsAdmin(User user) {
        if (user == null) {
            return false;
        } else {
            return user.isAdmin();
        }
    }
}

package com.example.offliner.service;

import com.example.offliner.domain.User;
import com.example.offliner.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class UserSevice implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }


    public void updateProfile(User user, String password, String email, String aboutYourself, String userChoice, String linkFacebook, String linkGoogle, String linkYoutube, String linkDribble, String linkLinkedIn, MultipartFile file) throws IOException {
        String userEmail = user.getEmail();
        String choice = user.getChoice();
        String aboutYourself1 = user.getAboutMyself();
        String linkFacebook1 = user.getLinkFacebook();
        String linkGoogle1 = user.getLinkGoogle();
        String linkYoutube1 = user.getLinkYoutube();
        String linkDribble1 = user.getLinkDribble();
        String linkLinkedIn1 = user.getLinkLinkedIn();
        boolean isAboutMyselfChanged = (aboutYourself != null && !aboutYourself.equals(aboutYourself1)) ||
                (aboutYourself1 != null && !aboutYourself1.equals(aboutYourself));
        if (isAboutMyselfChanged) {
            user.setAboutMyself(aboutYourself);
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
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            user.setAvatarFilename(resultFilename);
        }
        userRepo.save(user);
    }


}

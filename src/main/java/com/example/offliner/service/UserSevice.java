package com.example.offliner.service;

import com.example.offliner.repos.UserRepo;
import com.example.offliner.domain.User;
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


    public void updateProfile(User user, String password, String email, String aboutYourself, MultipartFile file) throws IOException {
        String userEmail = user.getEmail();
        String aboutYourself1 = user.getAboutMyself();
        boolean isAboutMyselfChanged = (aboutYourself != null && !aboutYourself.equals(aboutYourself1)) ||
                (aboutYourself1 != null && !aboutYourself1.equals(aboutYourself));
        if (isAboutMyselfChanged) {
            user.setAboutMyself(aboutYourself);
        }
        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));
        if (isEmailChanged) {
            user.setEmail(email);
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

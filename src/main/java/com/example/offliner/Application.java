package com.example.offliner;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dwnzejl4h",
                "api_key", "424976915584458",
                "api_secret", "TlQsPJt2OHBBSJVzwe31u3zFqgY"));
        SpringApplication.run(Application.class, args);
    }
}

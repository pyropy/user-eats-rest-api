package com.pyropy.usereats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// todo: implement spring security stuff
// @SpringBootApplication
@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
public class UsereatsApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsereatsApplication.class, args);
    }

}

package com.pyropy.usereats.service;

import com.pyropy.usereats.model.User;
import com.pyropy.usereats.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;


@Service("userService")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    /*
        Encrypts password and persists User to Database.
     */
    public User save(User user) {
        String encyptedPassword = passwordEncoder.encode(user.getPassword());
        return userRepository.save(
                new User(user.getFirstName(), user.getLastName(), user.getAddress(), encyptedPassword, user.getEmail(), user.getUsername()));
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }
}

package com.pyropy.usereats.service;

import com.pyropy.usereats.model.User;
import com.pyropy.usereats.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    /*
     *   Encrypts password and persists User to Database.
     */
    public User save(User user) {
        return userRepository.save(new User(user.getFirstName(), user.getLastName(),
                                user.getAddress(), passwordEncoder.encode(user.getPassword()),
                                user.getEmail(), user.getUsername()));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }
}

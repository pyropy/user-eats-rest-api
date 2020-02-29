package com.pyropy.usereats.api;

import com.pyropy.usereats.dao.UserRepository;
import com.pyropy.usereats.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @GetMapping("{id}")
    public Optional<User> getUserById(@PathVariable("id") long id) {
        return userRepository.findById(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(new User(user.getFirstName(), user.getLastName(), user.getAddress(), user.getPassword(), user.getEmail()));
    }
}

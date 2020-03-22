package com.pyropy.usereats.api;

import com.pyropy.usereats.model.User;
import com.pyropy.usereats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userService.findAll().forEach(users::add);
        return users;
    }

    @GetMapping("{id}")
    public Optional<User> getUserById(@PathVariable("id") String id) {
        return userService.findById(id);
    }

    @GetMapping("{username}")
    public User getUserByUsername(@PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    /*
     *
     */
    @PostMapping
    public User createUser(@RequestBody User user, @RequestParam(required = false) final boolean isRestaurantAdmin) {
        if (isRestaurantAdmin) userService.setResturantAdminRole(user);
        return userService.save(user);
    }
}

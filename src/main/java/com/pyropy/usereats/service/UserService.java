package com.pyropy.usereats.service;

import com.pyropy.usereats.model.Restaurant;
import com.pyropy.usereats.model.User;
import com.pyropy.usereats.repository.RoleRepository;
import com.pyropy.usereats.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }


    /*
     *   Encrypts password and persists User to Database.
     */
    public User save(User user) {
        return userRepository.save(new User(user.getFirstName(), user.getLastName(),
                user.getAddress(), passwordEncoder.encode(user.getPassword()),
                user.getEmail(), user.getUsername(), user.getRoles()));
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

    public User setResturantAdminRole(User user) {
        user.setRoles(Arrays.asList(roleRepository.findRoleByName("ROLE_RESTAURANT_ADMIN")));
        return user;
    }
}

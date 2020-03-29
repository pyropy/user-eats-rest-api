package com.pyropy.usereats.service;

import com.pyropy.usereats.dto.UserDto;
import com.pyropy.usereats.model.User;
import com.pyropy.usereats.repository.RoleRepository;
import com.pyropy.usereats.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    /*
     * Converts User Entity to UserDto class.
     * */
    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    /*
     * Converts UserDto to User Entity class.
     * */
    public User convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }


    /*
     *   Encrypts password and persists User to Database.
     */
    public UserDto createUser(UserDto userInfo) {
        User user = convertToEntity(userInfo);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userInfo.isRestaurantAdmin()) {
            setResturantAdminRole(user);
        }
        userRepository.save(user);
        return userInfo;
    }

    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return convertToDto(user);
    }

    public List<UserDto> findAll() {
        Iterable<User> users = userRepository.findAll();
        return StreamSupport
                .stream(users.spliterator(), false)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public User setResturantAdminRole(User user) {
        user.setRoles(Arrays.asList(roleRepository.findRoleByName("ROLE_RESTAURANT_ADMIN")));
        return user;
    }
}

package com.pyropy.usereats.service;

import com.pyropy.usereats.model.User;
import com.pyropy.usereats.repository.UserRepository;
import com.pyropy.usereats.model.UserModelDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserModelDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserModelDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(() ->
                            new UsernameNotFoundException(username));

        return new UserModelDetails(user);
    }
}

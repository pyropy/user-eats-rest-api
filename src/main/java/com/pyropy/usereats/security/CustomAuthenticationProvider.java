package com.pyropy.usereats.security;

import com.pyropy.usereats.model.Role;
import com.pyropy.usereats.model.User;
import com.pyropy.usereats.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Resource
    @Qualifier("passwordEncoder")
    private BCryptPasswordEncoder passwordEncoder;




    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        String encyptedPassword = passwordEncoder.encode(password);


        Optional<User> user = userRepository.findUserByUsername(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Username not found.");
        }

        if (user.get().getPassword().equals(encyptedPassword)) {
            throw new BadCredentialsException("Password incorrect.");
        }
        Set<Role> userRoles = user.get().getRoles();
        return new UsernamePasswordAuthenticationToken(
                username, password, userRoles.stream().map(x -> new SimpleGrantedAuthority(x.getName())).collect(Collectors.toList()));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

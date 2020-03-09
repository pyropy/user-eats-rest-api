package com.pyropy.usereats;

import com.pyropy.usereats.model.Role;
import com.pyropy.usereats.model.User;
import com.pyropy.usereats.repository.RoleRepository;
import com.pyropy.usereats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CommandLineApp implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
//            User admin = new User();
//            Role role = roleRepository.findRoleByName("ROLE_ADMIN");
//            Set<Role> roles  = new HashSet<>();
//            roles.add(role);
//            admin.setUsername("admin");
//            admin.setFirstName("admin");
//            admin.setLastName("admin");
//            admin.setPassword("admin");
//            admin.setEmail("admin@email.com");
//            admin.setAddress("admin");
//            admin.setRoles(roles);
//            userService.save(admin);
    }
}

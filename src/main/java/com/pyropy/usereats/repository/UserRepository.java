package com.pyropy.usereats.repository;

import com.pyropy.usereats.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findUserByUsername(String username);
}



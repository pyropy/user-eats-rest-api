package com.pyropy.usereats.repository;

import com.pyropy.usereats.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findUserByUsername(String username);
}



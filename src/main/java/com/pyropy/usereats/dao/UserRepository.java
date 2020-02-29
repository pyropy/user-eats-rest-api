package com.pyropy.usereats.dao;

import com.pyropy.usereats.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}

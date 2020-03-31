package com.pyropy.usereats.repository;

import com.pyropy.usereats.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, String> {
    Role findRoleByName(String name);
}

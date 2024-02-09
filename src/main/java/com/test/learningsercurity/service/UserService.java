package com.test.learningsercurity.service;

import com.test.learningsercurity.entity.ERole;
import com.test.learningsercurity.entity.Role;
import com.test.learningsercurity.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String userName, ERole roleName);
    Optional<User> getUser(String userName);
    List<User> getUser();
}

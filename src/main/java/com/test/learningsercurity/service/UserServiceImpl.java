package com.test.learningsercurity.service;

import com.test.learningsercurity.entity.ERole;
import com.test.learningsercurity.entity.Role;
import com.test.learningsercurity.entity.User;
import com.test.learningsercurity.entity.UserDto;
import com.test.learningsercurity.repo.RoleRepository;
import com.test.learningsercurity.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        log.info("Saving new user to the db {}", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to the db", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String userName, ERole roleName) {
        log.info("Adding role {} to user {}", userName, roleName);
        Optional<User> user = userRepository.findByUsername(userName);
        Role role = roleRepository.findByName(roleName.name());
        user.get().getRoles().add(role);
    }

    @Override
    public Optional<User> getUser(String userName) {
        log.info("Fetching user {}", userName);
        return userRepository.findByUsername(userName);
    }

    @Override
    public List<User> getUser() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()){
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        }
        User user = userOpt.get();

        return UserDto.build(user.getUsername(), user.getPassword(), user.getRoles() );

    }
}

package com.test.learningsercurity;

import com.test.learningsercurity.entity.ERole;
import com.test.learningsercurity.entity.Role;
import com.test.learningsercurity.entity.User;
import com.test.learningsercurity.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@Configuration
public class LearningSercurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearningSercurityApplication.class, args);
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    CommandLineRunner run(UserService userService) {
//        return args -> {
//            userService.saveRole(new Role(1l, ERole.ROLE_ADMIN));
//            userService.saveRole(new Role(2l, ERole.ROLE_USER));
//            userService.saveRole(new Role(3l, ERole.ROLE_MANAGER));
//            userService.saveRole(new Role(4l, ERole.ROLE_SUPER_ADMIN));
//
//            userService.saveUser(new User(1l,"bang1","username1","1234",new ArrayList<>()));
//            userService.saveUser(new User(2l,"bang2","username2","1234",new ArrayList<>()));
//            userService.saveUser(new User(3l,"bang3","username3","1234",new ArrayList<>()));
//            userService.saveUser(new User(4l,"bang4","username4","1234",new ArrayList<>()));
////
////
//            userService.addRoleToUser("username1",ERole.ROLE_USER);
//            userService.addRoleToUser("username2",ERole.ROLE_ADMIN);
//            userService.addRoleToUser("username3",ERole.ROLE_SUPER_ADMIN);
//            userService.addRoleToUser("username4",ERole.ROLE_MANAGER);
//        };
//    }
}

package com.test.learningsercurity.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class UserDto implements UserDetails {

    private String username;
    private String password;
    private Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

    public UserDto(String username, String password, Collection<Role> roles){
        this.username = username;
        this.password = password;
        roles.stream().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
        });
    }

    public static UserDto build(String username, String password, Collection<Role> roles) {
        return new UserDto( username,  password, roles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

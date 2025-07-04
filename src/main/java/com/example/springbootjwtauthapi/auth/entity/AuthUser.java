package com.example.springbootjwtauthapi.auth.entity;

import com.example.springbootjwtauthapi.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class AuthUser {

    private final Long userId;
    private final String username;
    private final Collection<? extends GrantedAuthority> authorities;

    @Builder
    public AuthUser(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.authorities = user.getUserRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }
}

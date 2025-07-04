package com.example.springbootjwtauthapi.user.entity;

import com.example.springbootjwtauthapi.user.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
public class User {

    private Long id;
    private String username;
    private String password;
    private String nickname;
    private Set<UserRole> userRoles = new HashSet<>();

    @Builder
    public User(Long id, String username, String password, String nickname, Set<UserRole> userRoles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.userRoles = userRoles != null ? userRoles : new HashSet<>();
    }

    public void addRole(UserRole userRole) {
        this.userRoles.add(userRole);
    }

}

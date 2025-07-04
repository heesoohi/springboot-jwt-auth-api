package com.example.springbootjwtauthapi.auth.dto;

import com.example.springbootjwtauthapi.user.enums.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder
@Getter
public class SignUpResponse {

    private String username;
    private String nickname;
    private Set<UserRole> userRoles;
}

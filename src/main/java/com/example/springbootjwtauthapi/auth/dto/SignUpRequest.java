package com.example.springbootjwtauthapi.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignUpRequest {

    private String username;
    private String password;
    private String nickname;
}

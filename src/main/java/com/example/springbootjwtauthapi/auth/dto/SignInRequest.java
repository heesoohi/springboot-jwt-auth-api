package com.example.springbootjwtauthapi.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignInRequest {

    private String username;
    private String password;
}

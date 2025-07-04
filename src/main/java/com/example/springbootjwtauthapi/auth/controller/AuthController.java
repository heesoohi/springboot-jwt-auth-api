package com.example.springbootjwtauthapi.auth.controller;

import com.example.springbootjwtauthapi.auth.dto.SignInRequest;
import com.example.springbootjwtauthapi.auth.dto.SignUpRequest;
import com.example.springbootjwtauthapi.auth.dto.SignUpResponse;
import com.example.springbootjwtauthapi.auth.dto.TokenResponse;
import com.example.springbootjwtauthapi.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Auth", description = "회원가입 및 로그인과 관련된 API")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "회원가입 API입니다.")
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(
            @RequestBody SignUpRequest request
    ) {
        return ResponseEntity.ok(authService.signUp(request));
    }

    @Operation(summary = "관리자 회원가입", description = "관리자 회원가입 API입니다.")
    @PostMapping("/signup/admin")
    public ResponseEntity<SignUpResponse> adminSignUp(
            @RequestBody SignUpRequest request
    ) {
        return ResponseEntity.ok(authService.adminSignUp(request));
    }

    @Operation(summary = "로그인", description = "로그인 API입니다.")
    @PostMapping("/signin")
    public ResponseEntity<TokenResponse> signin(
            @RequestBody SignInRequest request
    ) {
        return ResponseEntity.ok(authService.signIn(request));
    }
}

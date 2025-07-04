package com.example.springbootjwtauthapi.auth.service;

import com.example.springbootjwtauthapi.auth.dto.SignInRequest;
import com.example.springbootjwtauthapi.auth.dto.SignUpRequest;
import com.example.springbootjwtauthapi.auth.dto.SignUpResponse;
import com.example.springbootjwtauthapi.auth.dto.TokenResponse;
import com.example.springbootjwtauthapi.auth.jwt.JwtUtil;
import com.example.springbootjwtauthapi.exception.AuthException;
import com.example.springbootjwtauthapi.user.entity.User;
import com.example.springbootjwtauthapi.user.enums.UserRole;
import com.example.springbootjwtauthapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public SignUpResponse signUp(SignUpRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AuthException("USER_ALREADY_EXISTS", "이미 가입된 사용자입니다.", HttpStatus.BAD_REQUEST);
        }

        User user = User.builder()
                .username(request.getUsername())
                .nickname(request.getNickname())
                .password(passwordEncoder.encode(request.getPassword()))
                .userRoles(Set.of(UserRole.USER))
                .build();

        userRepository.save(user);

        return SignUpResponse.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .userRoles(user.getUserRoles())
                .build();
    }

    public SignUpResponse adminSignUp(SignUpRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AuthException("USER_ALREADY_EXISTS", "이미 가입된 사용자입니다.", HttpStatus.BAD_REQUEST);
        }

        User user = User.builder()
                .username(request.getUsername())
                .nickname(request.getNickname())
                .password(passwordEncoder.encode(request.getPassword()))
                .userRoles(Set.of(UserRole.USER))
                .build();

        userRepository.save(user);

        return SignUpResponse.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .userRoles(user.getUserRoles())
                .build();
    }

    public TokenResponse signIn(SignInRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AuthException("INVALID_CREDENTIALS", "해당 유저가 존재하지 않습니다.", HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthException("INVALID_CREDENTIALS", "비밀번호가 올바르지 않습니다.", HttpStatus.UNAUTHORIZED);
        }

        String token = jwtUtil.createAccessToken(user.getId(), user.getUsername(), user.getUserRoles());

        return new TokenResponse(token);
    }

    public SignUpResponse grantAdminRole(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AuthException("USER_NOT_FOUND", "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        user.addRole(UserRole.ADMIN);
        userRepository.save(user);

        return SignUpResponse.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .userRoles(user.getUserRoles())
                .build();
    }
}

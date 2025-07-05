package com.example.springbootjwtauthapi.auth.service;

import com.example.springbootjwtauthapi.auth.dto.SignUpRequest;
import com.example.springbootjwtauthapi.auth.dto.SignUpResponse;
import com.example.springbootjwtauthapi.auth.jwt.JwtUtil;
import com.example.springbootjwtauthapi.user.entity.User;
import com.example.springbootjwtauthapi.user.enums.UserRole;
import com.example.springbootjwtauthapi.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    private UserRepository userRepository = new UserRepository();
    private PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private JwtUtil jwtUtil = mock(JwtUtil.class);
    private AuthService authService = new AuthService(userRepository, passwordEncoder, jwtUtil);

    @Test
    void 회원가입_성공() {
        // given
        SignUpRequest request = SignUpRequest.builder()
                .username("testuser")
                .nickname("nickname")
                .password("password123")
                .build();

        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        // when
        SignUpResponse response = authService.signUp(request);

        //then
        Optional<User> saved = userRepository.findByUsername("testuser");
        assertTrue(saved.isPresent());
        User savedUser = saved.get();

        assertNotNull(savedUser.getId());
        assertEquals(request.getUsername(), savedUser.getUsername());
        assertEquals(request.getNickname(), savedUser.getNickname());
        assertEquals(Set.of(UserRole.USER), savedUser.getUserRoles());

        assertEquals(request.getUsername(), response.getUsername());
        assertEquals(request.getNickname(), response.getNickname());
    }

}

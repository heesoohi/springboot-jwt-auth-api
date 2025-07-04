package com.example.springbootjwtauthapi.auth.jwt;

import com.example.springbootjwtauthapi.auth.entity.AuthUser;
import com.example.springbootjwtauthapi.user.entity.User;
import com.example.springbootjwtauthapi.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenValue = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith("Bearer ")) {
            try {
                String token = jwtUtil.substringToken(tokenValue);

                if (jwtUtil.validate(token)) {
                    Claims claims = jwtUtil.extractClaims(token);
                    Long userId = Long.parseLong(claims.getSubject());

                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found in memory"));

                    AuthUser authUser = new AuthUser(user);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            authUser, null, authUser.getAuthorities()
                    );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                log.error("JWT Filter Error: {}", e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
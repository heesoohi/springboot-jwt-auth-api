package com.example.springbootjwtauthapi.auth.controller;

import com.example.springbootjwtauthapi.auth.dto.SignUpResponse;
import com.example.springbootjwtauthapi.auth.entity.AuthUser;
import com.example.springbootjwtauthapi.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Tag(name = "Admin", description = "관리자 관련 API")
public class AdminController {

    private final AuthService authService;

    @Operation(summary = "관리자 권한 부여", description = "특정 사용자에게 관리자 권한을 부여하는 API입니다.")
    @PatchMapping("/users/{userId}/roles")
    public ResponseEntity<SignUpResponse> grantAdminRole(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(authService.grantAdminRole(userId));
    }

    @Operation(summary = "관리자만 실행 가능한 API", description = "사용자가 관리자 권한을 가지고 있는지 확인하는 API입니다.")
    @GetMapping()
    public ResponseEntity<Void> verifyAdmin(
            @AuthenticationPrincipal AuthUser authUser
    ) {
        return ResponseEntity.ok().build();
    }
}

package com.sleephealth.auth.controller;

import com.sleephealth.auth.dto.LoginRequest;
import com.sleephealth.auth.dto.LoginResponse;
import com.sleephealth.auth.dto.RegisterRequest;
import com.sleephealth.auth.service.AuthService;
import com.sleephealth.common.Result;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return Result.success(response);
    }

    @PostMapping("/register")
    public Result<Void> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return Result.success("注册成功");
    }

    @GetMapping("/me")
    public Result<LoginResponse.UserInfo> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        com.sleephealth.user.entity.User user = authentication.getPrincipal() instanceof com.sleephealth.user.entity.User
                ? (com.sleephealth.user.entity.User) authentication.getPrincipal()
                : null;

        if (user != null) {
            return Result.success(LoginResponse.UserInfo.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .role(user.getRole())
                    .nickname(user.getNickname())
                    .build());
        }

        return Result.success(LoginResponse.UserInfo.builder()
                .username(username)
                .build());
    }
}

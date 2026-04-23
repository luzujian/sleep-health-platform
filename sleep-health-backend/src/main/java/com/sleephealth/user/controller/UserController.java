package com.sleephealth.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sleephealth.common.Result;
import com.sleephealth.user.entity.User;
import com.sleephealth.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('EXPERT')")
    public Result<Page<User>> getUserPage(
            @RequestParam(required = false) String role,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return Result.success(userService.getUserPage(role, page, size));
    }

    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        return Result.success(userService.getUserById(id));
    }

    @GetMapping("/experts")
    public Result<Page<User>> getExperts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return Result.success(userService.getUserPage("expert", page, size));
    }
}

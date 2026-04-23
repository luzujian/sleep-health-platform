package com.sleephealth.session.controller;

import com.sleephealth.common.Result;
import com.sleephealth.session.entity.MonitoringSession;
import com.sleephealth.session.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @GetMapping("/active/{userId}")
    public Result<MonitoringSession> getActiveSession(@PathVariable Long userId) {
        return Result.success(sessionService.getActiveSession(userId));
    }

    @PostMapping
    @PreAuthorize("hasRole('EXPERT')")
    public Result<MonitoringSession> createSession(
            @RequestParam Long userId,
            @RequestParam Long deviceId
    ) {
        return Result.success(sessionService.createSession(userId, deviceId));
    }

    @PutMapping("/{id}/end")
    public Result<Void> endSession(@PathVariable Long id) {
        sessionService.endSession(id);
        return Result.success();
    }

    @GetMapping("/user/{userId}")
    public Result<List<MonitoringSession>> getUserSessions(@PathVariable Long userId) {
        return Result.success(sessionService.getUserSessions(userId));
    }
}

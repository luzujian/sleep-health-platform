package com.sleephealth.alert.controller;

import com.sleephealth.alert.entity.Alert;
import com.sleephealth.alert.service.AlertService;
import com.sleephealth.common.Result;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @GetMapping
    @PreAuthorize("hasRole('EXPERT')")
    public Result<List<Alert>> getAlerts(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String level,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return Result.success(alertService.getAlerts(status, level, page, size));
    }

    @GetMapping("/user/{userId}")
    public Result<List<Alert>> getUserAlerts(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return Result.success(alertService.getUserAlerts(userId, page, size));
    }

    @GetMapping("/{id}")
    public Result<Alert> getAlertById(@PathVariable Long id) {
        return Result.success(alertService.getAlertById(id));
    }

    @PostMapping
    public Result<Alert> createAlert(@RequestBody Alert alert) {
        return Result.success(alertService.createAlert(alert));
    }

    @PostMapping("/{id}/handle")
    @PreAuthorize("hasRole('EXPERT')")
    public Result<Alert> handleAlert(
            @PathVariable Long id,
            @RequestBody HandleRequest request
    ) {
        return Result.success(alertService.handleAlert(id, request.getExpertId(), request.getHandleComment()));
    }

    @Data
    public static class HandleRequest {
        private Long expertId;
        private String handleComment;
    }
}

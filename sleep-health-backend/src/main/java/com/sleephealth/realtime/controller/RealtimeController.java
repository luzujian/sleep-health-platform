package com.sleephealth.realtime.controller;

import com.sleephealth.common.Result;
import com.sleephealth.realtime.entity.RealtimeVitals;
import com.sleephealth.realtime.service.RealtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/realtime")
@RequiredArgsConstructor
public class RealtimeController {

    private final RealtimeService realtimeService;

    @GetMapping("/{userId}")
    public Result<RealtimeVitals> getLatestVitals(@PathVariable Long userId) {
        return Result.success(realtimeService.getLatestVitals(userId));
    }

    @GetMapping("/{userId}/history")
    public Result<List<RealtimeVitals>> getVitalsHistory(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "60") int minutes
    ) {
        return Result.success(realtimeService.getVitalsHistory(userId, minutes));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('EXPERT')")
    public Result<List<RealtimeVitals>> getAllLatestVitals() {
        return Result.success(realtimeService.getAllLatestVitals());
    }

    @PostMapping
    public Result<RealtimeVitals> saveVitals(@RequestBody RealtimeVitals vitals) {
        return Result.success(realtimeService.saveVitals(vitals));
    }
}

package com.sleephealth.report.controller;

import com.sleephealth.common.Result;
import com.sleephealth.report.entity.SleepReport;
import com.sleephealth.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/{id}")
    public Result<SleepReport> getReportById(@PathVariable Long id) {
        return Result.success(reportService.getReportById(id));
    }

    @GetMapping
    public Result<List<SleepReport>> getUserReports(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return Result.success(reportService.getUserReports(userId, page, size));
    }

    @PostMapping
    public Result<SleepReport> createReport(@RequestBody SleepReport report) {
        return Result.success(reportService.createReport(report));
    }
}

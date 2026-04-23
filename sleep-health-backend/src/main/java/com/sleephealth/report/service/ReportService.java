package com.sleephealth.report.service;

import com.sleephealth.report.entity.SleepReport;

import java.util.List;

public interface ReportService {
    SleepReport getReportById(Long id);
    List<SleepReport> getUserReports(Long userId, int page, int size);
    SleepReport createReport(SleepReport report);
}

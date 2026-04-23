package com.sleephealth.report.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sleephealth.common.exception.BusinessException;
import com.sleephealth.common.Code;
import com.sleephealth.report.entity.SleepReport;
import com.sleephealth.report.mapper.SleepReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final SleepReportMapper reportMapper;

    @Override
    public SleepReport getReportById(Long id) {
        SleepReport report = reportMapper.selectById(id);
        if (report == null) {
            throw new BusinessException(Code.REPORT_NOT_FOUND, "报告不存在");
        }
        return report;
    }

    @Override
    public List<SleepReport> getUserReports(Long userId, int page, int size) {
        Page<SleepReport> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<SleepReport> wrapper = new LambdaQueryWrapper<SleepReport>()
                .eq(SleepReport::getUserId, userId)
                .orderByDesc(SleepReport::getReportDate);

        Page<SleepReport> result = reportMapper.selectPage(pageParam, wrapper);
        return result.getRecords();
    }

    @Override
    public SleepReport createReport(SleepReport report) {
        reportMapper.insert(report);
        return report;
    }
}

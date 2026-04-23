package com.sleephealth.alert.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sleephealth.alert.entity.Alert;
import com.sleephealth.alert.mapper.AlertMapper;
import com.sleephealth.common.exception.BusinessException;
import com.sleephealth.common.Code;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {

    private final AlertMapper alertMapper;

    @Override
    public Alert getAlertById(Long id) {
        Alert alert = alertMapper.selectById(id);
        if (alert == null) {
            throw new BusinessException(Code.ALERT_NOT_FOUND, "告警不存在");
        }
        return alert;
    }

    @Override
    public List<Alert> getAlerts(String status, String level, int page, int size) {
        Page<Alert> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Alert> wrapper = new LambdaQueryWrapper<>();

        if (status != null && !status.isEmpty()) {
            wrapper.eq(Alert::getStatus, status);
        }
        if (level != null && !level.isEmpty()) {
            wrapper.eq(Alert::getAlertLevel, level);
        }

        wrapper.orderByDesc(Alert::getCreatedAt);
        Page<Alert> result = alertMapper.selectPage(pageParam, wrapper);
        return result.getRecords();
    }

    @Override
    public List<Alert> getUserAlerts(Long userId, int page, int size) {
        Page<Alert> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<Alert> wrapper = new LambdaQueryWrapper<Alert>()
                .eq(Alert::getUserId, userId)
                .orderByDesc(Alert::getCreatedAt);

        Page<Alert> result = alertMapper.selectPage(pageParam, wrapper);
        return result.getRecords();
    }

    @Override
    public Alert createAlert(Alert alert) {
        alertMapper.insert(alert);
        return alert;
    }

    @Override
    public Alert handleAlert(Long id, Long expertId, String comment) {
        Alert alert = getAlertById(id);

        if ("handled".equals(alert.getStatus()) || "ignored".equals(alert.getStatus())) {
            throw new BusinessException(Code.ALERT_ALREADY_HANDLED, "告警已处理");
        }

        alert.setExpertId(expertId);
        alert.setStatus("handled");
        alert.setHandleComment(comment);
        alert.setHandledAt(LocalDateTime.now());
        alertMapper.updateById(alert);

        return alert;
    }
}

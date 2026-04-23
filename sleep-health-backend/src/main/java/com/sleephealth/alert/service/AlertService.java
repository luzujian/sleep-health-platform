package com.sleephealth.alert.service;

import com.sleephealth.alert.entity.Alert;

import java.util.List;

public interface AlertService {
    Alert getAlertById(Long id);
    List<Alert> getAlerts(String status, String level, int page, int size);
    List<Alert> getUserAlerts(Long userId, int page, int size);
    Alert createAlert(Alert alert);
    Alert handleAlert(Long id, Long expertId, String comment);
}

package com.sleephealth.session.service;

import com.sleephealth.session.entity.MonitoringSession;

import java.util.List;

public interface SessionService {
    MonitoringSession getActiveSession(Long userId);
    MonitoringSession createSession(Long userId, Long deviceId);
    void endSession(Long sessionId);
    List<MonitoringSession> getUserSessions(Long userId);
}

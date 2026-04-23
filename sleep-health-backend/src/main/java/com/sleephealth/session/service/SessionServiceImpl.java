package com.sleephealth.session.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sleephealth.common.exception.BusinessException;
import com.sleephealth.common.Code;
import com.sleephealth.session.entity.MonitoringSession;
import com.sleephealth.session.mapper.MonitoringSessionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final MonitoringSessionMapper sessionMapper;

    @Override
    public MonitoringSession getActiveSession(Long userId) {
        return sessionMapper.selectOne(
                new LambdaQueryWrapper<MonitoringSession>()
                        .eq(MonitoringSession::getUserId, userId)
                        .eq(MonitoringSession::getStatus, "active")
                        .orderByDesc(MonitoringSession::getStartTime)
                        .last("LIMIT 1")
        );
    }

    @Override
    public MonitoringSession createSession(Long userId, Long deviceId) {
        MonitoringSession session = new MonitoringSession();
        session.setUserId(userId);
        session.setDeviceId(deviceId);
        session.setStartTime(LocalDateTime.now());
        session.setStatus("active");
        sessionMapper.insert(session);
        return session;
    }

    @Override
    public void endSession(Long sessionId) {
        MonitoringSession session = sessionMapper.selectById(sessionId);
        if (session == null) {
            throw new BusinessException(Code.SESSION_NOT_FOUND, "会话不存在");
        }
        session.setEndTime(LocalDateTime.now());
        session.setStatus("ended");
        sessionMapper.updateById(session);
    }

    @Override
    public List<MonitoringSession> getUserSessions(Long userId) {
        return sessionMapper.selectList(
                new LambdaQueryWrapper<MonitoringSession>()
                        .eq(MonitoringSession::getUserId, userId)
                        .orderByDesc(MonitoringSession::getStartTime)
        );
    }
}

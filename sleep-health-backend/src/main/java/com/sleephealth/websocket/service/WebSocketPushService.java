package com.sleephealth.websocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sleephealth.websocket.handler.ExpertWebSocketHandler;
import com.sleephealth.websocket.handler.UserWebSocketHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketPushService {

    private final ExpertWebSocketHandler expertHandler;
    private final UserWebSocketHandler userHandler;
    private final ObjectMapper objectMapper;

    public void pushToExpert(Long userId, String event, Object data) {
        try {
            String json = objectMapper.writeValueAsString(Map.of(
                    "event", event,
                    "data", data
            ));
            expertHandler.sendToUser(userId, json);
        } catch (Exception e) {
            log.error("推送expert失败: userId={}, event={}", userId, event, e);
        }
    }

    public void pushToUser(String username, String event, Object data) {
        try {
            String json = objectMapper.writeValueAsString(Map.of(
                    "event", event,
                    "data", data
            ));
            userHandler.sendToUser(username, json);
        } catch (Exception e) {
            log.error("推送user失败: username={}, event={}", username, event, e);
        }
    }

    public void pushVitalsUpdate(Long userId, Object vitals) {
        pushToExpert(userId, "vitals:update", vitals);
        pushToUser(String.valueOf(userId), "vitals:update", vitals);
    }

    public void pushNewAlert(Object alert) {
        expertHandler.broadcast("alert:new", alert);
    }
}

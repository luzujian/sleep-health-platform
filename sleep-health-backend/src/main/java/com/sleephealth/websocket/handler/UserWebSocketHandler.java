package com.sleephealth.websocket.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class UserWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        String username = getUsernameFromSession(session);
        sessions.put(username, session);
        log.info("User WebSocket连接建立: username={}, sessionId={}", username, session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        log.info("User收到消息: sessionId={}, payload={}", session.getId(), message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String username = getUsernameFromSession(session);
        sessions.remove(username);
        log.info("User WebSocket连接关闭: username={}, status={}", username, status);
    }

    public void sendToUser(String username, String message) {
        WebSocketSession session = sessions.get(username);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                log.error("发送消息失败: username={}", username, e);
            }
        }
    }

    private String getUsernameFromSession(WebSocketSession session) {
        return session.getUri() != null ? session.getUri().getQuery() : "unknown";
    }
}

package com.sleephealth.config;

import com.sleephealth.websocket.handler.ExpertWebSocketHandler;
import com.sleephealth.websocket.handler.UserWebSocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final ExpertWebSocketHandler expertWebSocketHandler;
    private final UserWebSocketHandler userWebSocketHandler;

    @Value("${websocket.expert-path}")
    private String expertPath;

    @Value("${websocket.user-path}")
    private String userPath;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(expertWebSocketHandler, expertPath)
                .setAllowedOrigins("*");
        registry.addHandler(userWebSocketHandler, userPath)
                .setAllowedOrigins("*");
    }
}

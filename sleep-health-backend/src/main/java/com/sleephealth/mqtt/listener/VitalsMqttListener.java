package com.sleephealth.mqtt.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sleephealth.mqtt.dto.VitalsMessage;
import com.sleephealth.realtime.entity.RealtimeVitals;
import com.sleephealth.realtime.service.RealtimeService;
import com.sleephealth.websocket.service.WebSocketPushService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * MQTT消息监听器（骨架，暂不启用）
 * 实际使用时取消@ConditionalOnProperty注释
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "spring.mqtt", name = "enabled", havingValue = "true")
public class VitalsMqttListener {

    private final RealtimeService realtimeService;
    private final WebSocketPushService pushService;
    private final ObjectMapper objectMapper;

    // @Autowired
    // private MqttClient mqttClient;

    @PostConstruct
    public void init() {
        log.info("MQTT监听器初始化（暂未启用）");
        // 实际使用时在这里订阅MQTT Topic
        // subscribeToTopic("radar/+/telemetry/vitals");
    }

    public void handleVitalsMessage(String payload) {
        try {
            log.info("收到体征数据: {}", payload);

            VitalsMessage message = objectMapper.readValue(payload, VitalsMessage.class);

            RealtimeVitals vitals = new RealtimeVitals();
            vitals.setUserId(message.getUserId());
            vitals.setDeviceId(Long.parseLong(message.getDeviceId().replace("radar_", "")));
            vitals.setSessionId(message.getSessionId());
            vitals.setHeartRate(message.getVitals().getHeartRate());
            vitals.setBreathRate(message.getVitals().getBreathRate());
            vitals.setBodyMovement(message.getVitals().getBodyMovement());
            vitals.setSleepDepth(message.getVitals().getSleepDepth());
            vitals.setTimestamp(LocalDateTime.now());

            realtimeService.saveVitals(vitals);

            pushService.pushVitalsUpdate(message.getUserId(), vitals);

        } catch (Exception e) {
            log.error("处理体征消息失败: {}", payload, e);
        }
    }

    // private void subscribeToTopic(String topic) {
    //     mqttClient.subscribe(topic, (t, msg) -> {
    //         handleVitalsMessage(new String(msg.getPayload()));
    //     });
    // }
}

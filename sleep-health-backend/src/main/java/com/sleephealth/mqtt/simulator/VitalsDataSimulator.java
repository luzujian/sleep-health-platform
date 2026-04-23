package com.sleephealth.mqtt.simulator;

import com.sleephealth.realtime.entity.RealtimeVitals;
import com.sleephealth.realtime.service.RealtimeService;
import com.sleephealth.session.service.SessionService;
import com.sleephealth.websocket.service.WebSocketPushService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * 体征数据模拟器（本地模拟MQTT数据）
 * 每10秒生成一条模拟体征数据
 */
@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class VitalsDataSimulator {

    private final RealtimeService realtimeService;
    private final SessionService sessionService;
    private final WebSocketPushService pushService;

    private final Random random = new Random();

    // 模拟的用户ID列表
    private final Long[] userIds = {2L, 3L};

    @Scheduled(fixedRate = 10000) // 每10秒执行
    public void generateVitals() {
        for (Long userId : userIds) {
            try {
                RealtimeVitals vitals = generateRandomVitals(userId);
                realtimeService.saveVitals(vitals);

                log.info("模拟体征数据已保存: userId={}, heartRate={}, breathRate={}",
                        userId, vitals.getHeartRate(), vitals.getBreathRate());

                // 通过WebSocket推送
                pushService.pushVitalsUpdate(userId, vitals);

            } catch (Exception e) {
                log.error("生成模拟体征数据失败: userId={}", userId, e);
            }
        }
    }

    private RealtimeVitals generateRandomVitals(Long userId) {
        RealtimeVitals vitals = new RealtimeVitals();
        vitals.setUserId(userId);
        vitals.setDeviceId(1L); // 默认设备

        // 模拟正常范围数据
        vitals.setHeartRate(random.nextInt(20) + 55);  // 55-75
        vitals.setBreathRate(random.nextInt(6) + 12);  // 12-18
        vitals.setBodyMovement(random.nextInt(3));       // 0-2
        vitals.setSleepDepth(random.nextInt(4));         // 0-3
        vitals.setTimestamp(LocalDateTime.now());

        return vitals;
    }
}

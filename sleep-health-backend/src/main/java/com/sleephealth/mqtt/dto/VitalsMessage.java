package com.sleephealth.mqtt.dto;

import lombok.Data;

@Data
public class VitalsMessage {
    private String deviceId;
    private Long userId;
    private Long sessionId;
    private String timestamp;
    private Vitals vitals;

    @Data
    public static class Vitals {
        private Integer heartRate;
        private Integer breathRate;
        private Integer bodyMovement;
        private Integer sleepDepth;
    }
}

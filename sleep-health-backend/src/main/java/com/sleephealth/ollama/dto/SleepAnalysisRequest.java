package com.sleephealth.ollama.dto;

import lombok.Data;

@Data
public class SleepAnalysisRequest {
    private Long userId;
    private String date;
    private Integer sleepDuration;
    private Integer deepSleepDuration;
    private Integer lightSleepDuration;
    private Integer wakeCount;
    private Integer avgHeartRate;
    private Integer avgBreathRate;
}

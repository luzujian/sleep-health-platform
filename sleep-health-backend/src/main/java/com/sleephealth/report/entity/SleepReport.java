package com.sleephealth.report.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("sleep_reports")
public class SleepReport {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("report_date")
    private LocalDate reportDate;

    @TableField("session_id")
    private Long sessionId;

    @TableField("sleep_duration")
    private Integer sleepDuration;

    @TableField("deep_sleep_duration")
    private Integer deepSleepDuration;

    @TableField("light_sleep_duration")
    private Integer lightSleepDuration;

    @TableField("wake_count")
    private Integer wakeCount;

    @TableField("wake_duration")
    private Integer wakeDuration;

    @TableField("sleep_score")
    private Integer sleepScore;

    @TableField("avg_heart_rate")
    private BigDecimal avgHeartRate;

    @TableField("avg_breath_rate")
    private BigDecimal avgBreathRate;

    @TableField("ai_analysis")
    private String aiAnalysis;

    @TableField("ai_suggestions")
    private String aiSuggestions;

    @TableField("created_at")
    private LocalDateTime createdAt;
}

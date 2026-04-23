package com.sleephealth.realtime.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("realtime_vitals")
public class RealtimeVitals {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("session_id")
    private Long sessionId;

    @TableField("user_id")
    private Long userId;

    @TableField("device_id")
    private Long deviceId;

    @TableField("heart_rate")
    private Integer heartRate;

    @TableField("breath_rate")
    private Integer breathRate;

    @TableField("body_movement")
    private Integer bodyMovement;

    @TableField("sleep_depth")
    private Integer sleepDepth;

    private LocalDateTime timestamp;

    @TableField("created_at")
    private LocalDateTime createdAt;
}

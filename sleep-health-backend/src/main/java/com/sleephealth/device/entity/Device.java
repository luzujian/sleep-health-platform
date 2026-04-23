package com.sleephealth.device.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("devices")
public class Device {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("device_code")
    private String deviceCode;

    @TableField("device_name")
    private String deviceName;

    @TableField("device_type")
    private String deviceType;

    private String status;

    @TableField("last_heartbeat")
    private LocalDateTime lastHeartbeat;

    @TableField("created_at")
    private LocalDateTime createdAt;
}

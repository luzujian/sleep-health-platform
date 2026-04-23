package com.sleephealth.binding.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("device_bindings")
public class DeviceBinding {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("device_id")
    private Long deviceId;

    @TableField("bind_time")
    private LocalDateTime bindTime;

    @TableField("unbind_time")
    private LocalDateTime unbindTime;

    @TableField("is_active")
    private Boolean isActive;
}

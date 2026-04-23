package com.sleephealth.alert.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("alerts")
public class Alert {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("expert_id")
    private Long expertId;

    @TableField("alert_type")
    private String alertType;

    @TableField("alert_level")
    private String alertLevel;

    @TableField("vital_id")
    private Long vitalId;

    private String content;

    private String status;

    @TableField("handle_comment")
    private String handleComment;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("handled_at")
    private LocalDateTime handledAt;
}

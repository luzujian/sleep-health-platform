package com.sleephealth.common;

public class Code {
    public static final int SUCCESS = 200;
    public static final int ERROR = 500;

    // 认证相关 1000-1099
    public static final int AUTH_FAILED = 1001;
    public static final int TOKEN_EXPIRED = 1002;
    public static final int TOKEN_INVALID = 1003;
    public static final int UNAUTHORIZED = 1004;

    // 用户相关 2000-2099
    public static final int USER_NOT_FOUND = 2001;
    public static final int USERNAME_EXISTS = 2002;
    public static final int ROLE_INVALID = 2003;

    // 设备相关 3000-3099
    public static final int DEVICE_NOT_FOUND = 3001;
    public static final int DEVICE_OFFLINE = 3002;
    public static final int DEVICE_BINDING_NOT_FOUND = 3003;

    // 会话相关 4000-4099
    public static final int SESSION_NOT_FOUND = 4001;
    public static final int SESSION_NOT_ACTIVE = 4002;

    // 体征相关 5000-5099
    public static final int VITALS_NOT_FOUND = 5001;

    // 报告相关 6000-6099
    public static final int REPORT_NOT_FOUND = 6001;

    // 告警相关 7000-7099
    public static final int ALERT_NOT_FOUND = 7001;
    public static final int ALERT_ALREADY_HANDLED = 7002;
}

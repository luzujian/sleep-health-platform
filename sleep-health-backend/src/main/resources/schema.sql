-- =============================================
-- 睡眠健康管理平台 - 数据库初始化脚本
-- =============================================

-- 创建数据库 (需要在 PostgreSQL 中先执行: CREATE DATABASE sleep_health;)

-- 用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'user',
    nickname VARCHAR(100),
    phone VARCHAR(20),
    avatar_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);
CREATE INDEX IF NOT EXISTS idx_users_role ON users(role);

-- 设备表
CREATE TABLE IF NOT EXISTS devices (
    id BIGSERIAL PRIMARY KEY,
    device_code VARCHAR(100) UNIQUE NOT NULL,
    device_name VARCHAR(100),
    device_type VARCHAR(50) DEFAULT 'radar',
    status VARCHAR(20) DEFAULT 'offline',
    last_heartbeat TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_devices_code ON devices(device_code);
CREATE INDEX IF NOT EXISTS idx_devices_status ON devices(status);

-- 设备绑定表
CREATE TABLE IF NOT EXISTS device_bindings (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    device_id BIGINT NOT NULL REFERENCES devices(id) ON DELETE CASCADE,
    bind_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    unbind_time TIMESTAMP,
    is_active BOOLEAN DEFAULT TRUE
);

CREATE INDEX IF NOT EXISTS idx_bindings_user ON device_bindings(user_id);
CREATE INDEX IF NOT EXISTS idx_bindings_device ON device_bindings(device_id);
CREATE INDEX IF NOT EXISTS idx_bindings_active ON device_bindings(is_active) WHERE is_active = TRUE;

-- 监测会话表
CREATE TABLE IF NOT EXISTS monitoring_sessions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    device_id BIGINT NOT NULL REFERENCES devices(id) ON DELETE CASCADE,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP,
    status VARCHAR(20) DEFAULT 'active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_sessions_user ON monitoring_sessions(user_id);
CREATE INDEX IF NOT EXISTS idx_sessions_device ON monitoring_sessions(device_id);
CREATE INDEX IF NOT EXISTS idx_sessions_status ON monitoring_sessions(status);
CREATE INDEX IF NOT EXISTS idx_sessions_start ON monitoring_sessions(start_time);

-- 实时体征表
CREATE TABLE IF NOT EXISTS realtime_vitals (
    id BIGSERIAL PRIMARY KEY,
    session_id BIGINT REFERENCES monitoring_sessions(id) ON DELETE SET NULL,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    device_id BIGINT NOT NULL REFERENCES devices(id) ON DELETE CASCADE,
    heart_rate INTEGER,
    breath_rate INTEGER,
    body_movement INTEGER DEFAULT 0,
    sleep_depth INTEGER DEFAULT 0,
    timestamp TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_vitals_user_time ON realtime_vitals(user_id, timestamp DESC);
CREATE INDEX IF NOT EXISTS idx_vitals_session ON realtime_vitals(session_id);
CREATE INDEX IF NOT EXISTS idx_vitals_device_time ON realtime_vitals(device_id, timestamp DESC);

-- 睡眠报告表
CREATE TABLE IF NOT EXISTS sleep_reports (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    report_date DATE NOT NULL,
    session_id BIGINT REFERENCES monitoring_sessions(id) ON DELETE SET NULL,
    sleep_duration INTEGER,
    deep_sleep_duration INTEGER,
    light_sleep_duration INTEGER,
    wake_count INTEGER DEFAULT 0,
    wake_duration INTEGER DEFAULT 0,
    sleep_score INTEGER,
    avg_heart_rate DECIMAL(5,2),
    avg_breath_rate DECIMAL(5,2),
    ai_analysis TEXT,
    ai_suggestions TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_reports_user_date ON sleep_reports(user_id, report_date DESC);
CREATE INDEX IF NOT EXISTS idx_reports_date ON sleep_reports(report_date);

-- 告警表
CREATE TABLE IF NOT EXISTS alerts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    expert_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
    alert_type VARCHAR(50) NOT NULL,
    alert_level VARCHAR(20) NOT NULL,
    vital_id BIGINT REFERENCES realtime_vitals(id) ON DELETE SET NULL,
    content TEXT,
    status VARCHAR(20) DEFAULT 'pending',
    handle_comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    handled_at TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_alerts_user ON alerts(user_id);
CREATE INDEX IF NOT EXISTS idx_alerts_status ON alerts(status);
CREATE INDEX IF NOT EXISTS idx_alerts_type ON alerts(alert_type);
CREATE INDEX IF NOT EXISTS idx_alerts_level ON alerts(alert_level);
CREATE INDEX IF NOT EXISTS idx_alerts_created ON alerts(created_at DESC);

-- =============================================
-- 初始化测试数据
-- =============================================

-- 插入测试用户 (密码都是 123456，使用 BCrypt 加密)
INSERT INTO users (username, password, role, nickname, phone) VALUES
('expert1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', 'expert', '张专家', '13800138001'),
('user1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', 'user', '李用户', '13900139001'),
('user2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', 'user', '王用户', '13900139002')
ON CONFLICT (username) DO NOTHING;

-- 插入测试设备
INSERT INTO devices (device_code, device_name, device_type, status) VALUES
('radar_001', '毫米波雷达设备1号', 'radar', 'online'),
('radar_002', '毫米波雷达设备2号', 'radar', 'offline')
ON CONFLICT (device_code) DO NOTHING;

-- 绑定设备给用户
INSERT INTO device_bindings (user_id, device_id, is_active) VALUES
(2, 1, TRUE),
(3, 2, TRUE)
ON CONFLICT DO NOTHING;

-- 插入测试会话
INSERT INTO monitoring_sessions (user_id, device_id, start_time, status) VALUES
(2, 1, NOW() - INTERVAL '2 hours', 'active'),
(3, 2, NOW() - INTERVAL '30 minutes', 'active')
ON CONFLICT DO NOTHING;

-- 插入测试体征数据
INSERT INTO realtime_vitals (session_id, user_id, device_id, heart_rate, breath_rate, body_movement, sleep_depth, timestamp) VALUES
(1, 2, 1, 62, 15, 0, 2, NOW() - INTERVAL '1 hour'),
(1, 2, 1, 65, 16, 0, 2, NOW() - INTERVAL '50 minutes'),
(1, 2, 1, 68, 16, 1, 1, NOW() - INTERVAL '40 minutes'),
(2, 3, 2, 70, 17, 0, 3, NOW() - INTERVAL '20 minutes'),
(2, 3, 2, 72, 18, 0, 2, NOW() - INTERVAL '10 minutes')
ON CONFLICT DO NOTHING;

-- 插入测试睡眠报告
INSERT INTO sleep_reports (user_id, report_date, sleep_duration, deep_sleep_duration, light_sleep_duration, wake_count, sleep_score, avg_heart_rate, avg_breath_rate) VALUES
(2, CURRENT_DATE - 1, 420, 120, 280, 2, 85, 62.5, 15.8),
(3, CURRENT_DATE - 1, 380, 100, 250, 3, 78, 65.0, 16.2)
ON CONFLICT DO NOTHING;

-- 插入测试告警
INSERT INTO alerts (user_id, alert_type, alert_level, content, status) VALUES
(2, 'heart_rate_high', 'high', '用户心率异常，当前心率105bpm，超过阈值100bpm', 'pending'),
(3, 'breath_rate_low', 'medium', '用户呼吸率过低，当前呼吸率7次/分，低于阈值8次/分', 'handled')
ON CONFLICT DO NOTHING;

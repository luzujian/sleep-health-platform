# Web Dashboard 驾驶舱 — 设计规范

## 概述

将 `sleep-health-web` 的 Dashboard 重设计为专业的"睡眠健康监控驾驶舱"。面向后台监控人员，适合比赛展示和日常监控使用。

**不改后端接口，不使用 mock，所有数据来自真实 API。**

---

## 设计决策（已批准）

1. **加载态**: 骨架屏占位 + 数字渐入动画（双组合）
2. **图表**: 心率 + 呼吸率 + 睡眠深度三条线
3. **高风险用户**: 有 pending 告警优先，其次体征异常

---

## 页面结构

```
┌─────────────────────────────────────────────────────────┐
│  顶部欢迎区                                                │
│  Logo + 系统名 | 当前时间（实时刷新）| 登录用户 + 角色徽章  │
├──────────┬──────────┬──────────┬──────────────────────┤
│ 在线用户  │ 今日报告  │ 活跃告警  │ 数据上传率            │  ← 第一行 4卡片
├──────────┴──────────┴──────────┴──────────────────────┤
│  实时体征趋势图（心率+呼吸率+睡眠深度，三条线）           │  ← 第二行左
│  近7天告警分布（饼图或柱状图）                           │  ← 第二行右
├──────────────────────────┬──────────────────────────────┤
│  高风险用户列表（卡片式）   │  最新报告列表（表格）        │  ← 第三行
└──────────────────────────┴──────────────────────────────┘
```

---

## 数据来源

| 区块 | API | 说明 |
|------|-----|------|
| 在线用户数 | `GET /api/dashboard/stats` → `onlineUsers` | |
| 今日报告数 | `GET /api/dashboard/stats` → `todayReports` | |
| 活跃告警数 | `GET /api/dashboard/stats` → `alertCount` | |
| 数据上传率 | `GET /api/dashboard/stats` → `dataUploadRate` | |
| 实时趋势图 | `GET /api/realtime/all` | 所有用户最新体征，取最近20条滚动 |
| 告警分布 | `GET /api/alerts` | 按 level 分类统计 |
| 高风险用户 | `GET /api/users` + `GET /api/alerts?status=pending` | 有 pending 告警的用户优先 |
| 最新报告 | `GET /api/reports?page=1&size=5` | 最近5条报告 |

---

## 视觉规范

### 配色（来自 `_variables.scss`）

```
背景:     var(--web-bg)         #0F1923 深海军蓝
卡片:     var(--web-surface)    #1A2634
边框:     var(--web-border)     #2A3A4E
主色:     var(--web-primary)    #1E6FFF
在线/健康: var(--web-accent)    #00D4AA
告警高:   var(--web-high)       #FF6B35
告警中:   var(--web-medium)     #FFA502
告警低:   var(--web-low)       #3498DB
文字主:   var(--web-text-primary)  #FFFFFF
文字次:   var(--web-text-secondary) #8B9DB5
图表-心率: var(--chart-heartrate)   #FF4757
图表-呼吸: var(--chart-breathrate)  #1E6FFF
图表-深度: var(--chart-sleep-deep)  #2D5BFF
```

### 字号

```
页面大标题:  var(--web-font-h2)  24px
卡片标题:    var(--web-font-h3)  18px
正文:        var(--web-font-body) 14px
统计数字:    var(--web-font-num)  36px
辅助文字:    var(--web-font-small) 13px
```

### 间距

```
页面水平 padding: 24px
卡片间距 (gutter): 16px
卡片内边距: 16px
区块间距: 24px
```

---

## 组件设计

### 1. 顶部欢迎区

- 左侧: 系统名称"睡眠健康监控平台" + 简短标语
- 右侧: 实时时钟（每秒刷新） + 用户名 + 角色 tag
- 高度: 64px
- 背景: `var(--web-surface)` 带底边框

### 2. 统计指标卡片（第一行 × 4）

- 尺寸: 等宽 4 列
- 内容: 图标 + 数字 + 标签
- 数字: `var(--web-num)` 等宽字体, 36px
- 告警卡片: `alertCount > 0` 时边框变橙色 `var(--web-high)`
- 数字变化: 骨架屏 → 淡入动画
- 在线指示: 心形图标带脉冲动画

### 3. 实时体征趋势图（第二行左, 占比 2/3）

- 类型: ECharts 多线折线图
- 三条线: 心率(红) / 呼吸率(蓝) / 睡眠深度(深蓝, 归一化到相同量程)
- X轴: 时间（最近30个数据点，5秒刷新）
- Y轴: 左侧心率(bpm)，右侧呼吸率(次/分)，睡眠深度用独立Y轴
- 悬浮提示: 显示具体数值 + 时间戳
- 图例: 底部水平排列
- 动画: 数据点逐步推进，不重绘整张图
- 无数据时: 显示"等待数据中..."

### 4. 告警分布图（第二行右, 占比 1/3）

- 类型: ECharts 饼图 或 水平柱状图
- 数据: 从 `/api/alerts` 按 level 统计 pending 数量
- 颜色: HIGH=橙 / MEDIUM=黄 / LOW=蓝
- 中心: 显示总数
- 悬浮: 显示具体数量

### 5. 高风险用户列表（第三行左）

- 展示最近 5 条有 pending 告警的用户
- 每条: 用户名 + 告警类型图标 + 告警级别标签 + 时间
- CRITICAL/HIGH 行: 左侧 3px 彩色边条 + 浅色背景
- 点击: 跳转用户详情页 `/users/:id`
- 空状态: "当前无高风险用户 ✓"

### 6. 最新报告列表（第三行右）

- 展示最近 5 条报告
- 每条: 日期 + 用户名 + 睡眠评分 + 睡眠时长
- 评分: 用颜色标签（绿/蓝/橙/红）
- 点击: 跳转报告详情 `/reports/:id`
- 空状态: "暂无报告数据"

---

## 状态处理

### Loading 态
- 骨架屏占位: 灰色块动画
- 卡片骨架: 矩形占位块
- 图表骨架: 灰色背景 + 加载动画

### Empty 态
- 各区块独立空状态，不影响其他区块
- 图表空状态: 灰色背景 + "等待数据中..."
- 列表空状态: 居中图标 + 友好文案

### Error 态
- 各区块独立错误处理，不影响其他区块
- 显示错误消息 + 重试按钮

---

## API 保障（不改）

- 所有数据调用 `src/api/` 下的现有接口
- 不新增 API 调用
- 不改变接口路径和参数

---

## 文件修改清单

| 文件 | 操作 | 说明 |
|------|------|------|
| `src/views/dashboard/DashboardView.vue` | 重写 | 驾驶舱布局 + 组件 |
| `src/components/common/StatCard.vue` | 新增 | 统计卡片组件 |
| `src/components/dashboard/RealtimeChart.vue` | 新增 | 实时趋势图组件 |
| `src/components/dashboard/AlertDistribution.vue` | 新增 | 告警分布图组件 |
| `src/components/dashboard/HighRiskUserList.vue` | 新增 | 高风险用户列表组件 |
| `src/components/dashboard/LatestReportList.vue` | 新增 | 最新报告列表组件 |
| `src/styles/_variables.scss` | 已创建 | 变量已就绪 |
| `src/styles/_web-theme.scss` | 已创建 | 主题已就绪 |
| `src/styles/_transition.scss` | 已创建 | 动效已就绪 |

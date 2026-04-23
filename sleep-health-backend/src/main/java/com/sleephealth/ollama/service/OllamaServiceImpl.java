package com.sleephealth.ollama.service;

import com.sleephealth.ollama.dto.SleepAnalysisRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Ollama AI服务（骨架，暂未启用）
 * 实际使用时实现真正的Ollama API调用
 */
@Slf4j
@Service
@ConditionalOnProperty(prefix = "spring.ollama", name = "enabled", havingValue = "true")
public class OllamaServiceImpl implements OllamaService {

    // @Autowired
    // private RestTemplate restTemplate;

    // @Value("${spring.ollama.base-url}")
    // private String baseUrl;

    // @Value("${spring.ollama.model}")
    // private String model;

    @Override
    public String generateSleepAnalysis(SleepAnalysisRequest request) {
        log.info("生成睡眠分析: userId={}, date={}", request.getUserId(), request.getDate());

        // 实际实现时调用本地Ollama API
        // String prompt = buildPrompt(request);
        // return callOllama(prompt);

        // 骨架版本返回模拟数据
        return buildMockAnalysis(request);
    }

    private String buildMockAnalysis(SleepAnalysisRequest request) {
        return String.format("""
            您的睡眠质量评分：%d/100

            睡眠概况：
            - 总睡眠时长：%d分钟（约%d小时）
            - 深睡眠：%d分钟
            - 浅睡眠：%d分钟
            - 夜间醒来次数：%d次

            分析：
            您的睡眠结构整体正常，深度睡眠和浅睡眠比例合理。
            建议继续保持规律的作息时间。

            改善建议：
            1. 建议在22:00-23:00之间入睡
            2. 睡前避免使用电子设备
            3. 保持卧室温度在18-22度
            """,
                75 + request.getSleepDuration() / 20,
                request.getSleepDuration(),
                request.getSleepDuration() / 60,
                request.getDeepSleepDuration(),
                request.getLightSleepDuration(),
                request.getWakeCount()
        );
    }

    // private String callOllama(String prompt) {
    //     Map<String, Object> body = new HashMap<>();
    //     body.put("model", model);
    //     body.put("prompt", prompt);
    //     body.put("stream", false);
    //
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.setContentType(MediaType.APPLICATION_JSON);
    //
    //     ResponseEntity<Map> response = restTemplate.postForEntity(
    //             baseUrl + "/api/generate",
    //             new HttpEntity<>(body, headers),
    //             Map.class
    //     );
    //
    //     return (String) response.getBody().get("response");
    // }

    // private String buildPrompt(SleepAnalysisRequest request) {
    //     return String.format("""
    //         请分析以下睡眠数据并给出建议：
    //         - 睡眠日期：%s
    //         - 总睡眠时长：%d分钟
    //         - 深睡眠时长：%d分钟
    //         - 浅睡眠时长：%d分钟
    //         - 夜间醒来次数：%d次
    //         - 平均心率：%d bpm
    //         - 平均呼吸率：%d 次/分
    //
    //         请给出：
    //         1. 睡眠质量评分(0-100)
    //         2. 主要睡眠问题分析
    //         3. 改善建议
    //         """,
    //             request.getDate(),
    //             request.getSleepDuration(),
    //             request.getDeepSleepDuration(),
    //             request.getLightSleepDuration(),
    //             request.getWakeCount(),
    //             request.getAvgHeartRate(),
    //             request.getAvgBreathRate()
    //     );
    // }
}

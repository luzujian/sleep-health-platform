package com.sleephealth.ollama.service;

import com.sleephealth.ollama.dto.SleepAnalysisRequest;

public interface OllamaService {
    String generateSleepAnalysis(SleepAnalysisRequest request);
}

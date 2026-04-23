package com.sleephealth.realtime.service;

import com.sleephealth.realtime.entity.RealtimeVitals;

import java.util.List;

public interface RealtimeService {
    RealtimeVitals saveVitals(RealtimeVitals vitals);
    RealtimeVitals getLatestVitals(Long userId);
    List<RealtimeVitals> getVitalsHistory(Long userId, int minutes);
    List<RealtimeVitals> getAllLatestVitals();
}

package com.sleephealth.realtime.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sleephealth.realtime.entity.RealtimeVitals;
import com.sleephealth.realtime.mapper.RealtimeVitalsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RealtimeServiceImpl implements RealtimeService {

    private final RealtimeVitalsMapper vitalsMapper;

    @Override
    public RealtimeVitals saveVitals(RealtimeVitals vitals) {
        vitalsMapper.insert(vitals);
        return vitals;
    }

    @Override
    public RealtimeVitals getLatestVitals(Long userId) {
        return vitalsMapper.selectOne(
                new LambdaQueryWrapper<RealtimeVitals>()
                        .eq(RealtimeVitals::getUserId, userId)
                        .orderByDesc(RealtimeVitals::getTimestamp)
                        .last("LIMIT 1")
        );
    }

    @Override
    public List<RealtimeVitals> getVitalsHistory(Long userId, int minutes) {
        LocalDateTime startTime = LocalDateTime.now().minusMinutes(minutes);
        return vitalsMapper.selectList(
                new LambdaQueryWrapper<RealtimeVitals>()
                        .eq(RealtimeVitals::getUserId, userId)
                        .ge(RealtimeVitals::getTimestamp, startTime)
                        .orderByAsc(RealtimeVitals::getTimestamp)
        );
    }

    @Override
    public List<RealtimeVitals> getAllLatestVitals() {
        // 暂时返回所有用户的最新数据，实际可用SQL窗口函数优化
        return vitalsMapper.selectList(
                new LambdaQueryWrapper<RealtimeVitals>()
                        .orderByDesc(RealtimeVitals::getTimestamp)
                        .last("LIMIT 100")
        );
    }
}

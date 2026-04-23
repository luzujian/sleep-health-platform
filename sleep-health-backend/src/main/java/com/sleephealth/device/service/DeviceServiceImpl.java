package com.sleephealth.device.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sleephealth.common.exception.BusinessException;
import com.sleephealth.common.Code;
import com.sleephealth.device.entity.Device;
import com.sleephealth.device.mapper.DeviceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceMapper deviceMapper;

    @Override
    public List<Device> getAllDevices() {
        return deviceMapper.selectList(null);
    }

    @Override
    public Device getDeviceById(Long id) {
        Device device = deviceMapper.selectById(id);
        if (device == null) {
            throw new BusinessException(Code.DEVICE_NOT_FOUND, "设备不存在");
        }
        return device;
    }

    @Override
    public Device getDeviceByCode(String code) {
        Device device = deviceMapper.selectOne(
                new LambdaQueryWrapper<Device>().eq(Device::getDeviceCode, code)
        );
        if (device == null) {
            throw new BusinessException(Code.DEVICE_NOT_FOUND, "设备不存在");
        }
        return device;
    }

    @Override
    public void updateDeviceStatus(String code, String status) {
        Device device = getDeviceByCode(code);
        device.setStatus(status);
        device.setLastHeartbeat(LocalDateTime.now());
        deviceMapper.updateById(device);
    }
}

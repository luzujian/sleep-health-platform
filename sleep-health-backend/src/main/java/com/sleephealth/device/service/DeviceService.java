package com.sleephealth.device.service;

import com.sleephealth.device.entity.Device;

import java.util.List;

public interface DeviceService {
    List<Device> getAllDevices();
    Device getDeviceById(Long id);
    Device getDeviceByCode(String code);
    void updateDeviceStatus(String code, String status);
}

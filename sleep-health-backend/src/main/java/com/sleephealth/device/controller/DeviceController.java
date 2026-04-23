package com.sleephealth.device.controller;

import com.sleephealth.common.Result;
import com.sleephealth.device.entity.Device;
import com.sleephealth.device.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping
    @PreAuthorize("hasRole('EXPERT')")
    public Result<List<Device>> getAllDevices() {
        return Result.success(deviceService.getAllDevices());
    }

    @GetMapping("/{id}")
    public Result<Device> getDeviceById(@PathVariable Long id) {
        return Result.success(deviceService.getDeviceById(id));
    }
}

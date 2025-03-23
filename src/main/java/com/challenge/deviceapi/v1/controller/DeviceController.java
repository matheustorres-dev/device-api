package com.challenge.deviceapi.v1.controller;

import com.challenge.deviceapi.dto.DeviceDTO;
import com.challenge.deviceapi.dto.DeviceFilter;
import com.challenge.deviceapi.dto.request.DeviceCreateRequestDTO;
import com.challenge.deviceapi.dto.request.DeviceRequestDTO;
import com.challenge.deviceapi.service.impl.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/devices/{id}")
    public ResponseEntity<DeviceDTO> getDeviceById(@PathVariable String id) {
        log.info("API is getting device by Id: {}", id);

        final DeviceDTO response = deviceService.getDeviceById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/devices")
    public ResponseEntity<List<DeviceDTO>> getDevices(@RequestBody DeviceFilter deviceFilter) {
        log.info("API is getting devices with filter: {}", deviceFilter);

        final List<DeviceDTO> response = deviceService.getDevices(deviceFilter);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/devices")
    public ResponseEntity<DeviceDTO> createDevice(@RequestBody DeviceCreateRequestDTO deviceCreateRequest) {
        log.info("API is trying to create a new device: {}", deviceCreateRequest);

        return new ResponseEntity<>(deviceService.createDevice(deviceCreateRequest), HttpStatus.CREATED);
    }

    @PutMapping("/devices/{id}")
    public ResponseEntity<DeviceDTO> updateDevice(@PathVariable String id, @RequestBody DeviceRequestDTO deviceRequest) {
        log.info("API is trying to update a device: {}", id);

        return ResponseEntity.ok(deviceService.updateDevice(id, deviceRequest));
    }

    @PatchMapping("/devices/{id}")
    public ResponseEntity<DeviceDTO> updatePartialDevice(@PathVariable String id, @RequestBody DeviceRequestDTO deviceRequest) {
        log.info("API is trying to update partially a device: {} - {}", id, deviceRequest);

        return ResponseEntity.ok(deviceService.updateDevice(id, deviceRequest));
    }

    @DeleteMapping("/devices/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable String id) {
        log.info("API is trying to delete a device: {}", id);

        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }
}

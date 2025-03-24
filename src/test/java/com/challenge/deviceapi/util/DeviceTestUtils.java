package com.challenge.deviceapi.util;

import com.challenge.deviceapi.dto.DeviceDTO;
import com.challenge.deviceapi.dto.DeviceFilter;
import com.challenge.deviceapi.dto.request.DeviceCreateRequestDTO;
import com.challenge.deviceapi.dto.request.DeviceUpdateRequestDTO;
import com.challenge.deviceapi.enumeration.DeviceState;
import com.challenge.deviceapi.model.Device;

import java.time.LocalDateTime;

public class DeviceTestUtils {

    public static final String deviceId = "D3viceId1";
    public static final String deviceName = "Test Name";
    public static final String deviceBrand = "Test Brand";
    public static final DeviceState deviceStateAvailable = DeviceState.AVAILABLE;
    public static final LocalDateTime deviceCreationDateTime = LocalDateTime.now();

    public static Device generateDeviceEntity() {
        return Device.builder()
                .id(deviceId)
                .name(deviceName)
                .brand(deviceBrand)
                .state(deviceStateAvailable)
                .creationDate(deviceCreationDateTime)
                .build();
    }

    public static DeviceDTO generateDeviceDTO() {
        return DeviceDTO.builder()
                .id(deviceId)
                .name(deviceName)
                .brand(deviceBrand)
                .state(deviceStateAvailable)
                .creationDate(deviceCreationDateTime)
                .build();
    }

    public static DeviceFilter generateEmptyFilter() {
        return DeviceFilter.builder()
                .build();
    }

    public static DeviceCreateRequestDTO generateValidCreateDeviceRequest() {
        return DeviceCreateRequestDTO.builder()
                .name(deviceName)
                .brand(deviceBrand)
                .state(deviceStateAvailable)
                .build();
    }

    public static DeviceUpdateRequestDTO generateValidUpdateDeviceRequest() {
        return DeviceUpdateRequestDTO.builder()
                .name(deviceName)
                .brand(deviceBrand)
                .state(deviceStateAvailable)
                .build();
    }

    public static DeviceUpdateRequestDTO generateInvalidUpdateDeviceRequest() {
        return DeviceUpdateRequestDTO.builder()
                .name("")
                .brand("")
                .build();
    }
}

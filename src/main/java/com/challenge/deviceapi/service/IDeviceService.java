package com.challenge.deviceapi.service;

import com.challenge.deviceapi.dto.DeviceDTO;
import com.challenge.deviceapi.dto.DeviceFilter;
import com.challenge.deviceapi.dto.request.DeviceCreateRequestDTO;
import com.challenge.deviceapi.dto.request.DeviceUpdateRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IDeviceService {

    DeviceDTO getDeviceById(final String id);
    List<DeviceDTO> getDevices(final DeviceFilter deviceFilter);
    DeviceDTO createDevice(final DeviceCreateRequestDTO deviceRequest);
    DeviceDTO updateDevice(final String deviceId, final DeviceUpdateRequestDTO deviceRequest);
    void deleteDevice(final String deviceId);
}

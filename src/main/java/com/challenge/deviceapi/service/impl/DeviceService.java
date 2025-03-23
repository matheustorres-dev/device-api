package com.challenge.deviceapi.service.impl;

import com.challenge.deviceapi.dto.DeviceDTO;
import com.challenge.deviceapi.dto.DeviceFilter;
import com.challenge.deviceapi.dto.request.DeviceRequestDTO;
import com.challenge.deviceapi.exception.DeviceNotFoundException;
import com.challenge.deviceapi.mapper.DeviceMapper;
import com.challenge.deviceapi.model.Device;
import com.challenge.deviceapi.repository.DeviceRepository;
import com.challenge.deviceapi.service.IDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DeviceService implements IDeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public DeviceDTO getDeviceById(final String id) {
        return DeviceMapper.toDTO(this.findDeviceById(id));
    }

    @Override
    public List<DeviceDTO> getDevices(final DeviceFilter deviceFilter) {
        log.info("Getting devices with filter: {}", deviceFilter);

        List<Device> devices = new ArrayList<>();

        if (!ObjectUtils.isEmpty(deviceFilter) && !deviceFilter.isEmpty()) {

            if (StringUtils.isBlank(deviceFilter.getName()))
                devices = deviceRepository.findByName(deviceFilter.getName());

            if (StringUtils.isBlank(deviceFilter.getBrand()))
                devices = deviceRepository.findByBrand(deviceFilter.getBrand());
        } else {
            devices = deviceRepository.findAll();
        }

        log.info("Parsing list of {}", (ObjectUtils.isEmpty(devices) ? 0 : devices.size()));

        if (ObjectUtils.isEmpty(devices))
            throw new DeviceNotFoundException();
        else
            return devices.stream()
                .map(DeviceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DeviceDTO createDevice(final DeviceRequestDTO deviceRequest) {
        log.info("Creating a device: {}", deviceRequest);

        Device createdDevice = deviceRepository.save(DeviceMapper.requestToEntity(deviceRequest));
        return DeviceMapper.toDTO(createdDevice);
    }

    @Override
    public DeviceDTO updateDevice(final String deviceId, final DeviceRequestDTO deviceRequest) {
        log.info("Updating a device by Id: {} - {}", deviceId, deviceRequest);

        Device existingDevice = this.findDeviceById(deviceId);

        if (!StringUtils.isBlank(deviceRequest.getName()))
            existingDevice.setName(deviceRequest.getName());

        if (!StringUtils.isBlank(deviceRequest.getBrand()))
            existingDevice.setBrand(deviceRequest.getBrand());

        Device updatedDevice = deviceRepository.save(existingDevice);
        return DeviceMapper.toDTO(updatedDevice);
    }

    @Override
    public void deleteDevice(final String deviceId) {
        log.info("Deleting a device by Id: {}", deviceId);
        Device deviceToDelete = this.findDeviceById(deviceId);

        deviceRepository.deleteById(deviceToDelete.getId());
    }

    private Device findDeviceById(String deviceId) {
        log.info("Getting a device by Id: {}", deviceId);

        return deviceRepository.findById(deviceId)
                .orElseThrow(DeviceNotFoundException::new);
    }
}

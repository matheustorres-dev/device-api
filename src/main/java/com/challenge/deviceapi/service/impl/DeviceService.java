package com.challenge.deviceapi.service.impl;

import com.challenge.deviceapi.dto.DeviceDTO;
import com.challenge.deviceapi.dto.DeviceFilter;
import com.challenge.deviceapi.dto.request.DeviceCreateRequestDTO;
import com.challenge.deviceapi.dto.request.DeviceUpdateRequestDTO;
import com.challenge.deviceapi.enumeration.DeviceState;
import com.challenge.deviceapi.exception.DeviceInUseException;
import com.challenge.deviceapi.exception.DeviceInvalidException;
import com.challenge.deviceapi.exception.DeviceNotFoundException;
import com.challenge.deviceapi.mapper.DeviceMapper;
import com.challenge.deviceapi.model.Device;
import com.challenge.deviceapi.repository.DeviceRepository;
import com.challenge.deviceapi.service.IDeviceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.*;
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

            if (!StringUtils.isBlank(deviceFilter.getBrand()))
                devices = deviceRepository.findByBrand(deviceFilter.getBrand());

            if (deviceFilter.getState() != null)
                devices = deviceRepository.findByState(deviceFilter.getState());

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
    public DeviceDTO createDevice(final DeviceCreateRequestDTO deviceRequest) {
        log.info("Creating a device: {}", deviceRequest);

        final Device newDevice = DeviceMapper.requestToEntity(deviceRequest);
        newDevice.setCreationDate(LocalDateTime.now());

        final DeviceDTO createdDevice = DeviceMapper.toDTO(deviceRepository.save(newDevice));

        log.info("Device created successfully: {}", createdDevice);
        return createdDevice;
    }

    @Override
    public DeviceDTO updateDevice(final String deviceId, final DeviceUpdateRequestDTO deviceRequest) {
        log.info("Updating a device by Id: {} - {}", deviceId, deviceRequest);

        if (StringUtils.isBlank(deviceRequest.getName()) && StringUtils.isBlank(deviceRequest.getBrand()) && deviceRequest.getState() == null) {
            log.error("Device data to update is invalid {}.", deviceRequest);
            throw new DeviceInvalidException();
        }

        Device existingDevice = this.findDeviceById(deviceId);

        if (DeviceState.IN_USE.equals(existingDevice.getState())) {

            if (!StringUtils.isBlank(deviceRequest.getName()) || !StringUtils.isBlank(deviceRequest.getBrand())) {

                log.error("Device {} is IN_USE, cannot proceed to UPDATE the NAME or BRAND.", deviceId);
                throw new DeviceInUseException();
            } else if (deviceRequest.getState() != null) {

                log.info("Device {} is IN_USE, but is allow to UPDATE the STATE to {}.", deviceId, deviceRequest.getState());
                existingDevice.setState(deviceRequest.getState());
            }

        } else {
            log.info("Device {} not IN_USE, able to UPDATE the with the current state {}", deviceId, existingDevice.getState());

            if (!StringUtils.isBlank(deviceRequest.getName()))
                existingDevice.setName(deviceRequest.getName());

            if (!StringUtils.isBlank(deviceRequest.getBrand()))
                existingDevice.setBrand(deviceRequest.getBrand());

            if (deviceRequest.getState() != null)
                existingDevice.setState(deviceRequest.getState());
        }

        DeviceDTO updatedDevice = DeviceMapper.toDTO(deviceRepository.save(existingDevice));

        log.info("Device updated successfully: {}", updatedDevice);
        return updatedDevice;
    }

    @Override
    public void deleteDevice(final String deviceId) {
        log.info("Deleting a device by Id: {}", deviceId);

        Device deviceToDelete = this.findDeviceById(deviceId);

        if (DeviceState.IN_USE.equals(deviceToDelete.getState())) {
            log.error("Device {} is IN_USE, cannot proceed with deletion.", deviceId);
            throw new DeviceInUseException();
        }
        else {
            log.info("Device {} not IN_USE, able to DELETE with the current state {}", deviceId, deviceToDelete.getState());
            deviceRepository.deleteById(deviceToDelete.getId());
        }

        log.info("Device {} deleted successfully", deviceId);
    }

    private Device findDeviceById(String deviceId) {
        log.info("Getting a device by Id: {}", deviceId);

        return deviceRepository.findById(deviceId)
                .orElseThrow(DeviceNotFoundException::new);
    }
}

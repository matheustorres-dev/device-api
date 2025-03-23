package com.challenge.deviceapi.service.impl;

import com.challenge.deviceapi.dto.DeviceDTO;
import com.challenge.deviceapi.enumeration.DeviceState;
import com.challenge.deviceapi.exception.DeviceNotFoundException;
import com.challenge.deviceapi.mapper.DeviceMapper;
import com.challenge.deviceapi.model.Device;
import com.challenge.deviceapi.repository.DeviceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeviceServiceTest {
    
    @InjectMocks
    private DeviceService deviceService;
    
    @Mock
    private DeviceRepository deviceRepository;
    
    private final String deviceId = "D3viceId1";
    private final String deviceName = "Test Name";
    private final String deviceBrand = "Test Brand";
    private final DeviceState deviceStateAvailable = DeviceState.AVAILABLE;
    private final LocalDateTime deviceCreationDateTime = LocalDateTime.now();
    
    public DeviceServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetDeviceByIdAndDeviceExistsThenReturnDeviceTest() {

        final Device mockDevice = Device.builder()
                .id(deviceId)
                .name(deviceName)
                .brand(deviceBrand)
                .state(deviceStateAvailable)
                .creationDate(deviceCreationDateTime)
                .build();
        
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(mockDevice));

        final DeviceDTO result = deviceService.getDeviceById(deviceId);

        assertNotNull(result);
        assertEquals(deviceId, result.getId());
        assertEquals(deviceName, result.getName());
        assertEquals(deviceBrand, result.getBrand());
        assertEquals(deviceStateAvailable, result.getState());
        assertEquals(deviceCreationDateTime, result.getCreationDate());
        verify(deviceRepository, times(1)).findById(deviceId);
    }

    @Test
    void testGetDeviceByIdNotFound() {
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> deviceService.getDeviceById(deviceId));
        verify(deviceRepository, only()).findById(deviceId);
    }
}
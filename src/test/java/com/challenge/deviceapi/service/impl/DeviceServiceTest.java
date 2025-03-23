package com.challenge.deviceapi.service.impl;

import com.challenge.deviceapi.dto.DeviceDTO;
import com.challenge.deviceapi.exception.DeviceNotFoundException;
import com.challenge.deviceapi.model.Device;
import com.challenge.deviceapi.repository.DeviceRepository;
import com.challenge.deviceapi.util.DeviceTestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.challenge.deviceapi.util.DeviceTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeviceServiceTest {
    
    @InjectMocks
    private DeviceService deviceService;
    
    @Mock
    private DeviceRepository deviceRepository;
    
    public DeviceServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenGetDeviceByIdAndDeviceExistsThenReturnDeviceTest() {

        final Device deviceGenerated = DeviceTestUtils.generateDeviceEntity();
        
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(deviceGenerated));

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
    void whenGetDeviceByIdAndDeviceNotFoundThenReturnNotFoundExceptionTest() {
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> deviceService.getDeviceById(deviceId));
        verify(deviceRepository, only()).findById(deviceId);
    }
}
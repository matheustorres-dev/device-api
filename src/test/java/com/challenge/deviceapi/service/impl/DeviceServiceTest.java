package com.challenge.deviceapi.service.impl;

import com.challenge.deviceapi.dto.DeviceDTO;
import com.challenge.deviceapi.dto.request.DeviceCreateRequestDTO;
import com.challenge.deviceapi.enumeration.DeviceState;
import com.challenge.deviceapi.exception.DeviceInUseException;
import com.challenge.deviceapi.exception.DeviceInvalidException;
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
    void whenGetDeviceByIdButDeviceNotFoundThenReturnNotFoundExceptionTest() {
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> deviceService.getDeviceById(deviceId));
        verify(deviceRepository, only()).findById(deviceId);
    }

    @Test
    void whenCreateDeviceWithValidRequestThenReturnCreatedDeviceTest() {
        final DeviceCreateRequestDTO request = generateValidCreateDeviceRequest();
        final Device device = DeviceTestUtils.generateDeviceEntity();

        when(deviceRepository.save(any(Device.class))).thenReturn(device);

        final DeviceDTO result = deviceService.createDevice(request);

        assertNotNull(result);
        assertEquals(deviceName, result.getName());
        assertEquals(deviceBrand, result.getBrand());
        assertEquals(deviceStateAvailable, result.getState());
        verify(deviceRepository, times(1)).save(any(Device.class));
    }

    @Test
    void whenDeleteDeviceAndDeviceExistsThenDeleteSuccessfullyTest() {
        final Device deviceGenerated = DeviceTestUtils.generateDeviceEntity();

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(deviceGenerated));
        doNothing().when(deviceRepository).deleteById(deviceId);

        assertDoesNotThrow(() -> deviceService.deleteDevice(deviceId));
        verify(deviceRepository, times(1)).findById(deviceId);
        verify(deviceRepository, times(1)).deleteById(deviceId);
    }

    @Test
    void whenDeleteDeviceButDeviceInUseThenThrowDeviceInUseExceptionTest() {
        final Device deviceGenerated = DeviceTestUtils.generateDeviceEntity();
        deviceGenerated.setState(DeviceState.IN_USE);

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(deviceGenerated));

        assertThrows(DeviceInUseException.class, () -> deviceService.deleteDevice(deviceId));
        verify(deviceRepository, only()).findById(deviceId);
    }

    @Test
    void whenDeleteDeviceButDeviceNotFoundThenThrowDeviceNotFoundExceptionTest() {
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> deviceService.deleteDevice(deviceId));
        verify(deviceRepository, only()).findById(deviceId);
    }
}
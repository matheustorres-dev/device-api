package com.challenge.deviceapi.service.impl;

import com.challenge.deviceapi.dto.DeviceDTO;
import com.challenge.deviceapi.dto.DeviceFilter;
import com.challenge.deviceapi.dto.request.DeviceCreateRequestDTO;
import com.challenge.deviceapi.dto.request.DeviceUpdateRequestDTO;
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

import java.util.Collections;
import java.util.List;
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
    void whenGetDevicesWithEmptyFilterThenReturnAllDevicesTest() {
        final DeviceFilter filter = generateEmptyFilter();
        final List<Device> devices = List.of(generateDeviceEntity(), generateDeviceEntity());

        when(deviceRepository.findAll()).thenReturn(devices);

        final List<DeviceDTO> result = deviceService.getDevices(filter);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(deviceRepository, only()).findAll();
    }

    @Test
    void whenGetDevicesWithNullFilterThenReturnAllDevicesTest() {
        final DeviceFilter filter = null;
        final List<Device> devices = List.of(generateDeviceEntity(), generateDeviceEntity());

        when(deviceRepository.findAll()).thenReturn(devices);

        final List<DeviceDTO> result = deviceService.getDevices(filter);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(deviceRepository, only()).findAll();
    }

    @Test
    void whenGetDevicesWithBrandFilterThenReturnFilteredDevicesTest() {
        final DeviceFilter filter = DeviceFilter.builder().brand(deviceBrand).build();
        final List<Device> devices = List.of(generateDeviceEntity());

        when(deviceRepository.findByBrand(deviceBrand)).thenReturn(devices);

        final List<DeviceDTO> result = deviceService.getDevices(filter);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(deviceBrand, result.getFirst().getBrand());
        verify(deviceRepository, only()).findByBrand(deviceBrand);
    }

    @Test
    void whenGetDevicesWithStateFilterThenReturnFilteredDevicesTest() {
        final DeviceFilter filter = DeviceFilter.builder().state(deviceStateAvailable).build();
        final List<Device> devices = List.of(generateDeviceEntity());

        when(deviceRepository.findByState(deviceStateAvailable)).thenReturn(devices);

        final List<DeviceDTO> result = deviceService.getDevices(filter);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(deviceName, result.getFirst().getName());
        verify(deviceRepository, only()).findByState(deviceStateAvailable);
    }

    @Test
    void whenGetDevicesWithFilterAndNoDevicesFoundThenThrowDeviceNotFoundExceptionTest() {
        final DeviceFilter filter = DeviceFilter.builder().state(deviceStateAvailable).build();

        when(deviceRepository.findByState(deviceStateAvailable)).thenReturn(Collections.emptyList());

        assertThrows(DeviceNotFoundException.class, () -> deviceService.getDevices(filter));
        verify(deviceRepository, only()).findByState(deviceStateAvailable);
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
        assertNotNull(result.getCreationDate());
        verify(deviceRepository, only()).save(any(Device.class));
    }

    @Test
    void whenUpdateDeviceWithValidRequestThenReturnUpdatedDeviceTest() {
        final DeviceUpdateRequestDTO request = DeviceTestUtils.generateValidUpdateDeviceRequest();
        final Device device = DeviceTestUtils.generateDeviceEntity();

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));
        when(deviceRepository.save(any(Device.class))).thenReturn(device);

        final DeviceDTO result = deviceService.updateDevice(deviceId, request);

        assertNotNull(result);
        assertEquals(deviceName, result.getName());
        assertEquals(deviceBrand, result.getBrand());
        assertEquals(deviceStateAvailable, result.getState());
        assertNotNull(result.getCreationDate());
        verify(deviceRepository, times(1)).findById(deviceId);
        verify(deviceRepository, times(1)).save(any(Device.class));
    }

    @Test
    void whenPartiallyUpdateDeviceWithOnlyNameRequestThenReturnUpdatedDeviceTest() {
        final String updatedName = "updatedName";

        final DeviceUpdateRequestDTO request = DeviceUpdateRequestDTO.builder()
                .name(updatedName)
                .build();

        final Device device = DeviceTestUtils.generateDeviceEntity();

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));
        when(deviceRepository.save(any(Device.class))).thenReturn(device);

        final DeviceDTO result = deviceService.updateDevice(deviceId, request);

        assertNotNull(result);
        assertEquals(updatedName, result.getName());
        assertEquals(deviceBrand, result.getBrand());
        assertEquals(deviceStateAvailable, result.getState());
        assertNotNull(result.getCreationDate());
        verify(deviceRepository, times(1)).findById(deviceId);
        verify(deviceRepository, times(1)).save(any(Device.class));
    }

    @Test
    void whenPartiallyUpdateDeviceWithOnlyBrandRequestThenReturnUpdatedDeviceTest() {
        final String updatedBrand = "updatedBrand";

        final DeviceUpdateRequestDTO request = DeviceUpdateRequestDTO.builder()
                .brand(updatedBrand)
                .build();

        final Device device = DeviceTestUtils.generateDeviceEntity();

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));
        when(deviceRepository.save(any(Device.class))).thenReturn(device);

        final DeviceDTO result = deviceService.updateDevice(deviceId, request);

        assertNotNull(result);
        assertEquals(deviceName, result.getName());
        assertEquals(updatedBrand, result.getBrand());
        assertEquals(deviceStateAvailable, result.getState());
        assertNotNull(result.getCreationDate());
        verify(deviceRepository, times(1)).findById(deviceId);
        verify(deviceRepository, times(1)).save(any(Device.class));
    }

    @Test
    void whenUpdateDeviceWithInvalidRequestThenThrowDeviceInvalidExceptionTest() {
        final DeviceUpdateRequestDTO request = DeviceTestUtils.generateInvalidUpdateDeviceRequest();

        assertThrows(DeviceInvalidException.class, () -> deviceService.updateDevice(deviceId, request));
        verify(deviceRepository, never()).findById(deviceId);
    }

    @Test
    void whenUpdateDeviceNotFoundThenThrowDeviceNotFoundExceptionTest() {
        final DeviceUpdateRequestDTO request = DeviceTestUtils.generateValidUpdateDeviceRequest();

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> deviceService.updateDevice(deviceId, request));
        verify(deviceRepository, only()).findById(deviceId);
    }

    @Test
    void whenUpdateDeviceInUseThenThrowDeviceInUseExceptionTest() {
        final Device device = DeviceTestUtils.generateDeviceEntity();
        device.setState(DeviceState.IN_USE);

        final DeviceUpdateRequestDTO request = DeviceTestUtils.generateValidUpdateDeviceRequest();

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));

        assertThrows(DeviceInUseException.class, () -> deviceService.updateDevice(deviceId, request));
        verify(deviceRepository, only()).findById(deviceId);
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
    void whenDeleteDeviceButDeviceNotFoundThenThrowDeviceNotFoundExceptionTest() {
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> deviceService.deleteDevice(deviceId));
        verify(deviceRepository, only()).findById(deviceId);
    }

    @Test
    void whenDeleteDeviceButDeviceInUseThenThrowDeviceInUseExceptionTest() {
        final Device deviceGenerated = DeviceTestUtils.generateDeviceEntity();
        deviceGenerated.setState(DeviceState.IN_USE);

        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(deviceGenerated));

        assertThrows(DeviceInUseException.class, () -> deviceService.deleteDevice(deviceId));
        verify(deviceRepository, only()).findById(deviceId);
    }
}
package com.challenge.deviceapi.v1.controller;

import com.challenge.deviceapi.dto.DeviceDTO;
import com.challenge.deviceapi.dto.DeviceFilter;
import com.challenge.deviceapi.dto.request.DeviceCreateRequestDTO;
import com.challenge.deviceapi.dto.request.DeviceUpdateRequestDTO;
import com.challenge.deviceapi.exception.DeviceInvalidException;
import com.challenge.deviceapi.exception.DeviceNotFoundException;
import com.challenge.deviceapi.service.impl.DeviceService;
import com.challenge.deviceapi.util.DeviceTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.challenge.deviceapi.util.DeviceTestUtils.deviceId;
import static com.challenge.deviceapi.util.DeviceTestUtils.deviceName;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeviceController.class)
class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DeviceService deviceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getDeviceById_shouldReturnDevice_whenDeviceExists() throws Exception {
        final DeviceDTO response = DeviceTestUtils.generateDeviceDTO();

        when(deviceService.getDeviceById(deviceId)).thenReturn(response);

        mockMvc.perform(get("/devices/{id}", deviceId)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void getDeviceById_shouldReturnNotFound_whenDeviceDoesNotExist() throws Exception {
        when(deviceService.getDeviceById(deviceId)).thenThrow(new DeviceNotFoundException());

        mockMvc.perform(get("/devices/{id}", deviceId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getDevices_shouldReturnDeviceList_whenDevicesExist() throws Exception {
        final DeviceDTO deviceGenerated = DeviceTestUtils.generateDeviceDTO();

        final DeviceFilter filter = DeviceFilter.builder()
                .name(deviceName)
                .build();

        final List<DeviceDTO> deviceList = List.of(deviceGenerated, deviceGenerated);

        when(deviceService.getDevices(filter)).thenReturn(deviceList);

        mockMvc.perform(get("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filter)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(deviceList)));
    }

    @Test
    void getDevices_shouldReturnNotFound_whenNoDevicesMatchFilter() throws Exception {

        final DeviceFilter filter = DeviceFilter.builder()
                .name(deviceName)
                .build();

        when(deviceService.getDevices(filter)).thenThrow(new DeviceNotFoundException());

        mockMvc.perform(get("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filter)))
                .andExpect(status().isNotFound());
    }

    @Test
    void getDevices_shouldReturnNotFound_whenDeviceFilterIsInvalid() throws Exception {

        final DeviceFilter filter = DeviceTestUtils.generateEmptyFilter();

        when(deviceService.getDevices(filter)).thenThrow(new DeviceNotFoundException());

        mockMvc.perform(get("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filter)))
                .andExpect(status().isNotFound());
    }

    @Test
    void createDevice_shouldReturnCreatedDevice_whenDeviceIsValid() throws Exception {
        final DeviceCreateRequestDTO request = DeviceTestUtils.generateValidCreateDeviceRequest();

        final DeviceDTO response = DeviceTestUtils.generateDeviceDTO();

        when(deviceService.createDevice(request)).thenReturn(response);

        mockMvc.perform(post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    /*
    @Test
    void createDevice_shouldReturnBadRequest_whenDeviceHasValidationError() throws Exception {
        final DeviceCreateRequestDTO invalidRequest = DeviceTestUtils.generateInvalidCreateDeviceRequest();

        mockMvc.perform(post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
     */

    @Test
    void updateDevice_shouldReturnUpdatedDevice_whenRequestIsValid() throws Exception {
        final DeviceUpdateRequestDTO request = DeviceTestUtils.generateValidUpdateDeviceRequest();

        final DeviceDTO response = DeviceTestUtils.generateDeviceDTO();

        when(deviceService.updateDevice(deviceId, request)).thenReturn(response);

        mockMvc.perform(put("/devices/{id}", deviceId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void updateDevice_shouldReturnNotFound_whenDeviceDoesNotExist() throws Exception {
        final DeviceUpdateRequestDTO request = DeviceTestUtils.generateValidUpdateDeviceRequest();

        when(deviceService.updateDevice(deviceId, request)).thenThrow(new DeviceNotFoundException());

        mockMvc.perform(put("/devices/{id}", deviceId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateDevice_shouldReturnBadRequest_whenRequestIsInvalid() throws Exception {
        final DeviceUpdateRequestDTO invalidRequest = DeviceTestUtils.generateInvalidUpdateDeviceRequest();

        when(deviceService.updateDevice(deviceId, invalidRequest)).thenThrow(new DeviceInvalidException());

        mockMvc.perform(put("/devices/{id}", deviceId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void patchDevice_shouldReturnUpdatedDevice_whenRequestIsValid() throws Exception {
        final DeviceUpdateRequestDTO request = DeviceTestUtils.generateValidUpdateDeviceRequest();

        final DeviceDTO response = DeviceTestUtils.generateDeviceDTO();

        when(deviceService.updateDevice(deviceId, request)).thenReturn(response);

        mockMvc.perform(patch("/devices/{id}", deviceId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void patchDevice_shouldReturnNotFound_whenDeviceDoesNotExist() throws Exception {
        final DeviceUpdateRequestDTO request = DeviceTestUtils.generateValidUpdateDeviceRequest();

        when(deviceService.updateDevice(deviceId, request)).thenThrow(new DeviceNotFoundException());

        mockMvc.perform(patch("/devices/{id}", deviceId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void patchDevice_shouldReturnBadRequest_whenRequestIsInvalid() throws Exception {
        final DeviceUpdateRequestDTO invalidRequest = DeviceTestUtils.generateInvalidUpdateDeviceRequest();

        when(deviceService.updateDevice(deviceId, invalidRequest)).thenThrow(new DeviceInvalidException());

        mockMvc.perform(patch("/devices/{id}", deviceId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteDevice_shouldReturnNoContent_whenDeviceIsDeletedSuccessfully() throws Exception {
        mockMvc.perform(delete("/devices/{id}", deviceId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteDevice_shouldReturnNotFound_whenDeviceDoesNotExist() throws Exception {
        doThrow(new DeviceNotFoundException()).when(deviceService).deleteDevice(deviceId);

        mockMvc.perform(delete("/devices/{id}", deviceId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
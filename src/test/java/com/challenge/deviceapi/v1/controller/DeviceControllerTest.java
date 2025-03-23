package com.challenge.deviceapi.v1.controller;

import com.challenge.deviceapi.dto.DeviceDTO;
import com.challenge.deviceapi.exception.DeviceNotFoundException;
import com.challenge.deviceapi.service.impl.DeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private final String deviceId = "12345";
    private final String deviceName = "Test Device";

    @Test
    void getDeviceById_shouldReturnDevice_whenDeviceExists() throws Exception {
        final DeviceDTO response = DeviceDTO.builder()
                .id(deviceId)
                .name(deviceName)
                .build();

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
}
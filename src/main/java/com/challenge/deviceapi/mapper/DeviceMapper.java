package com.challenge.deviceapi.mapper;


import com.challenge.deviceapi.dto.DeviceDTO;
import com.challenge.deviceapi.dto.request.DeviceCreateRequestDTO;
import com.challenge.deviceapi.dto.request.DeviceUpdateRequestDTO;
import com.challenge.deviceapi.model.Device;
import org.modelmapper.ModelMapper;

public class DeviceMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Device requestToEntity(DeviceCreateRequestDTO deviceCreateRequest) {
        if (deviceCreateRequest == null) {
            return null;
        }

        return modelMapper.map(deviceCreateRequest, Device.class);
    }

    public static DeviceDTO toDTO(Device device) {
        if (device == null) {
            return null;
        }

        return modelMapper.map(device, DeviceDTO.class);
    }
}

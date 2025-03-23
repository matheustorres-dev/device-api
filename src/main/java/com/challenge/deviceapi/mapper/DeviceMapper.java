package com.challenge.deviceapi.mapper;


import com.challenge.deviceapi.dto.DeviceDTO;
import com.challenge.deviceapi.dto.request.DeviceRequestDTO;
import com.challenge.deviceapi.model.Device;
import org.modelmapper.ModelMapper;

public class DeviceMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static Device requestToEntity(DeviceRequestDTO deviceRequest) {
        if (deviceRequest == null) {
            return null;
        }

        return modelMapper.map(deviceRequest, Device.class);
    }

    public static Device toEntity(DeviceDTO deviceDTO) {
        if (deviceDTO == null) {
            return null;
        }

        return modelMapper.map(deviceDTO, Device.class);
    }

    public static DeviceDTO toDTO(Device device) {
        if (device == null) {
            return null;
        }

        return modelMapper.map(device, DeviceDTO.class);
    }
}

package com.challenge.deviceapi.mapper;

import com.challenge.deviceapi.dto.DeviceDTO;
import com.challenge.deviceapi.model.Device;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

public class DeviceMapperTest {

    @Test
    void whenMappingNullDeviceCreateRequestDTOToEntityThenReturnNullEntity() {

        final DeviceMapper mapper = new DeviceMapper();

        final Device deviceEntityMapped = mapper.requestToEntity(null);

        assertNull(deviceEntityMapped);
    }

    @Test
    void whenMappingNullDeviceEntityToDTOThenReturnNullDTO() {

        final DeviceDTO deviceDTOMapped = DeviceMapper.toDTO(null);

        assertNull(deviceDTOMapped);
    }
}

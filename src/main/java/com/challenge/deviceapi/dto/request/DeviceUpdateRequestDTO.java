package com.challenge.deviceapi.dto.request;

import com.challenge.deviceapi.enumeration.DeviceState;
import lombok.*;

@Data
@Builder
@ToString
public class DeviceUpdateRequestDTO {

    private String name;
    private String brand;
    private DeviceState state;
}

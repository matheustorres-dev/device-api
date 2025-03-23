package com.challenge.deviceapi.dto.request;

import com.challenge.deviceapi.enumeration.DeviceState;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
public class DeviceRequestDTO {

    private String name;
    private String brand;
    private DeviceState state;
    private LocalDateTime creationDate;
}

package com.challenge.deviceapi.dto;

import com.challenge.deviceapi.enumeration.DeviceState;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class DeviceDTO {

    private String id;
    private String name;
    private String brand;
    private DeviceState state;
    private LocalDateTime creationDate;
}

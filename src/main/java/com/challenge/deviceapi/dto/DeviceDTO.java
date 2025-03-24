package com.challenge.deviceapi.dto;

import com.challenge.deviceapi.enumeration.DeviceState;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDTO {

    private String id;
    private String name;
    private String brand;
    private DeviceState state;
    private LocalDateTime creationDate;
}

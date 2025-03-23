package com.challenge.deviceapi.dto.request;

import com.challenge.deviceapi.enumeration.DeviceState;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class DeviceCreateRequestDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String brand;
    @NotBlank
    private DeviceState state;
}

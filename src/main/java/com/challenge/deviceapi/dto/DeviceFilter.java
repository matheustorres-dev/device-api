package com.challenge.deviceapi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
@ToString
public class DeviceFilter {

    private String name;
    private String brand;

    public boolean isEmpty() {
        return StringUtils.isBlank(name) && StringUtils.isBlank(brand);
    }
}

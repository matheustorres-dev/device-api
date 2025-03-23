package com.challenge.deviceapi.dto;

import com.challenge.deviceapi.enumeration.DeviceState;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
@ToString
public class DeviceFilter {

    private String brand;
    private DeviceState state;

    public boolean isEmpty() {
        return StringUtils.isBlank(brand) && state == null;
    }
}

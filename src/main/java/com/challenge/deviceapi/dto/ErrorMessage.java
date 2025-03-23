package com.challenge.deviceapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage {

    private int httpStatus;
    private String message;

}

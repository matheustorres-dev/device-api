package com.challenge.deviceapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DeviceInvalidException extends RuntimeException {

    public DeviceInvalidException() {
        super("Device data to update is invalid.");
    }
}
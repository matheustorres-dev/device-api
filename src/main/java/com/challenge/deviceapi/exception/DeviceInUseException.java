package com.challenge.deviceapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DeviceInUseException extends RuntimeException {

    public DeviceInUseException() {
        super("Cannot apply changes to a device in use.");
    }
}
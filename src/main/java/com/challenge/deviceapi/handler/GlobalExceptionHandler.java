package com.challenge.deviceapi.handler;

import com.challenge.deviceapi.dto.ErrorMessage;
import com.challenge.deviceapi.exception.DeviceInUseException;
import com.challenge.deviceapi.exception.DeviceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(DeviceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleNotFoundException(DeviceNotFoundException ex) {
        return new ResponseEntity<>(ErrorMessage.builder().message(ex.getMessage())
                                                            .httpStatus(HttpStatus.NOT_FOUND.value())
                                                            .build(),
                                                            HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DeviceInUseException.class)
    public ResponseEntity<ErrorMessage> handleConflictException(DeviceInUseException ex) {
        return new ResponseEntity<>(ErrorMessage.builder().message(ex.getMessage())
                .httpStatus(HttpStatus.CONFLICT.value())
                .build(),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGeneralException(Exception ex) {
        return new ResponseEntity<>(ErrorMessage.builder().message("An unexpected error occurred")
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
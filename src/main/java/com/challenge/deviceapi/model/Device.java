package com.challenge.deviceapi.model;

import com.challenge.deviceapi.enumeration.DeviceState;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode
public class Device {

    @Id
    private String id;

    private String name;
    private String brand;
    private DeviceState state;
    private LocalDateTime creationDate;

}
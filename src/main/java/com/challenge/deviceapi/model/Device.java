package com.challenge.deviceapi.model;

import com.challenge.deviceapi.enumeration.DeviceState;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Device {

    @Id
    @UuidGenerator
    private String id;

    private String name;
    private String brand;
    private DeviceState state;
    private LocalDateTime creationDate;

}
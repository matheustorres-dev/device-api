package com.challenge.deviceapi.repository;

import com.challenge.deviceapi.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {

    List<Device> findByName(final String name);
    List<Device> findByBrand(final String brand);
}
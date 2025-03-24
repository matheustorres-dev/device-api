package com.challenge.deviceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.challenge.deviceapi")
public class DeviceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeviceApiApplication.class, args);
	}

}

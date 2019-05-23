package com.iot.smart.water.meter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IotSmartWaterMeterApplication {

	public static void main(String[] args) {
		SpringApplication.run(IotSmartWaterMeterApplication.class, args);
	}

}


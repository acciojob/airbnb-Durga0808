package com.driver;

import com.driver.controllers.HotelManagementController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackageClasses = HotelManagementController.class)
@SpringBootApplication
public class AirBnb {

	public static void main(String[] args) {
		SpringApplication.run(AirBnb.class, args);
	}

}

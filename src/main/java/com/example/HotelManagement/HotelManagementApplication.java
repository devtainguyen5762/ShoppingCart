package com.example.HotelManagement;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.example.HotelManagement.model.FileStorageProperties;

@SpringBootApplication
@EntityScan(basePackageClasses= {
		HotelManagementApplication.class,
		Jsr310JpaConverters.class 
})
@EnableConfigurationProperties({
	FileStorageProperties.class
})
public class HotelManagementApplication {

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC+7"));
	}
	public static void main(String[] args) {
		SpringApplication.run(HotelManagementApplication.class, args);
	}
}

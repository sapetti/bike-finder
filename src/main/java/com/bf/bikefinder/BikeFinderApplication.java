package com.bf.bikefinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BikeFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(BikeFinderApplication.class, args);
	}
}

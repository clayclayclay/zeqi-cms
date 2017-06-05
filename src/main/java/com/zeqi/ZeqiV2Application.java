package com.zeqi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ZeqiV2Application {

	public static void main(String[] args) {
		SpringApplication.run(ZeqiV2Application.class, args);
	}
}

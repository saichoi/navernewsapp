package com.cos.navernewsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class NaverNewsappApplication {

	public static void main(String[] args) {
		SpringApplication.run(NaverNewsappApplication.class, args);
	}

}

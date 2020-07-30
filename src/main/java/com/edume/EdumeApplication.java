package com.edume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EdumeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdumeApplication.class, args);
	}
}

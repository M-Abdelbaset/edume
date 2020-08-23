package com.edume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.edume.EdumeApplication.CacheConfiguration;

@SpringBootApplication
@Import({CacheConfiguration.class})
public class EdumeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdumeApplication.class, args);
	}
	
	@Configuration
	@EnableCaching
	public static class CacheConfiguration {}
}

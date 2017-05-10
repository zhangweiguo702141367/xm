package com.zh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class AppServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppServerApplication.class, args);
	}
}

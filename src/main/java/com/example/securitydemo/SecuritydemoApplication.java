package com.example.securitydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

///To control access of classes that are in different packages.
@SpringBootApplication(scanBasePackages = {"com.example.securitydemo", "com.securitydemo"})
public class SecuritydemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecuritydemoApplication.class, args);
	}

}

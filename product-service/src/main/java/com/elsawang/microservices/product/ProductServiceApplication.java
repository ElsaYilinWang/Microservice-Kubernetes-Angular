package com.elsawang.microservices.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Product Service microservice application
 * 
 * @SpringBootApplication combines:
 * - @Configuration: Tags the class as a source of bean definitions
 * - @EnableAutoConfiguration: Enables Spring Boot's auto-configuration
 * - @ComponentScan: Enables component scanning in the current package
 */
@SpringBootApplication
public class ProductServiceApplication {

	/**
	 * Main method that starts the Spring Boot application
	 * Uses SpringApplication.run() to bootstrap and launch the application
	 * 
	 * @param args Command line arguments passed to the application
	 */
	public static void main(String[] args) {
		// Launch the Spring Boot application with the specified configuration class
		SpringApplication.run(ProductServiceApplication.class, args);
	}

}

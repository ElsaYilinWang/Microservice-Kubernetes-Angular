package com.elsawang.microservices.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the API Gateway application.
 * 
 * @SpringBootApplication annotation combines:
 * - @Configuration: Tags the class as a source of bean definitions
 * - @EnableAutoConfiguration: Tells Spring Boot to auto-configure the application
 * - @ComponentScan: Enables component scanning in the current package
 */
@SpringBootApplication
public class ApiGatewayApplication {

	/**
	 * Main method that starts the Spring Boot application.
	 * Uses SpringApplication.run() to bootstrap and launch the application.
	 * 
	 * @param args Command line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}

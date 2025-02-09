package com.elsawang.microservices.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Order Service application.
 * 
 * @SpringBootApplication annotation combines:
 * - @Configuration: Tags the class as a source of bean definitions
 * - @EnableAutoConfiguration: Tells Spring Boot to auto-configure the application
 * - @ComponentScan: Enables component scanning in the current package
 */
@SpringBootApplication
public class OrderServiceApplication {

    /**
     * Main method that starts the Spring Boot application.
     * 
     * @param args Command line arguments passed to the application
     */
    public static void main(String[] args) {
        try {
            SpringApplication.run(OrderServiceApplication.class, args);
        } catch (Exception e) {
            // Log any startup errors and exit
            System.err.println("Failed to start Order Service: " + e.getMessage());
            System.exit(1);
        }
    }

}

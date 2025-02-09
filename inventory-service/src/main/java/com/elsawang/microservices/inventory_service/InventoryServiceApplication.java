package com.elsawang.microservices.inventory_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Inventory Service.
 * 
 * This service manages product inventory tracking, including:
 * - SKU code and quantity management
 * - Stock availability checks
 * - Integration with MySQL database
 *
 * The @SpringBootApplication annotation enables:
 * - Auto-configuration
 * - Component scanning
 * - Additional configuration settings
 */
@SpringBootApplication
public class InventoryServiceApplication {

    /**
     * Main method to bootstrap the Spring Boot application.
     * Initializes the application context and starts the embedded server.
     *
     * @param args Command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

}

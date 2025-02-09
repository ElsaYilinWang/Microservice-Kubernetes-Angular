package com.elsawang.microservices.inventory_service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;

/**
 * Integration tests for the Inventory Service using TestContainers and REST Assured.
 * Tests the inventory availability endpoint with various scenarios.
 */
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryServiceApplicationTests {

    private static final String SKU_CODE = "iphone_15";
    private static final String INVENTORY_CHECK_ENDPOINT = "/api/inventory/check";

    @Container
    @ServiceConnection
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.4.0");

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    @DisplayName("Should verify inventory availability with valid and invalid quantities")
    void shouldReadInventory() {
        // Test with valid quantity (1 unit)
        boolean inStock = given()
                .queryParam("skuCode", SKU_CODE)
                .queryParam("quantity", 1)
                .when()
                .get(INVENTORY_CHECK_ENDPOINT)
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .extract()
                .as(Boolean.class);
        assertTrue(inStock, "Should have sufficient stock for 1 unit");

        // Test with excessive quantity (1000 units)
        boolean notInStock = given()
                .queryParam("skuCode", SKU_CODE)
                .queryParam("quantity", 1000)
                .when()
                .get(INVENTORY_CHECK_ENDPOINT)
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .extract()
                .as(Boolean.class);
        assertFalse(notInStock, "Should not have sufficient stock for 1000 units");
    }
}

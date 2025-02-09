package com.elsawang.microservices.product;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

import io.restassured.RestAssured;

/**
 * Integration tests for the Product Service application
 * Uses TestContainers for MongoDB integration testing and REST Assured for API testing
 */
@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

    // MongoDB container for integration testing
    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

    // Captures the random port assigned by Spring Boot
    @LocalServerPort
    private Integer port;

    // Static initialization of MongoDB container
    static {
        mongoDBContainer.start();
    }

    /**
     * Setup method runs before each test
     * Configures REST Assured with the dynamic test server port
     */
    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    /**
     * Tests the product creation endpoint
     * Verifies:
     * - Correct HTTP status code (201 Created)
     * - Product ID generation
     * - Proper response field matching with request data
     */
    @Test
    void shouldCreateProduct() {
        // Test data in JSON format
        String requestBody = """
                {
                    "name": "iPhone 15",
                    "description": "iPhone 15 is a smartphone from Apple",
                    "price": 1000
                }""";

        // API test using REST Assured's fluent API
        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                    .post("/api/product")
                .then()
                    .statusCode(201)
                    .body("id", Matchers.notNullValue())
                    .body("name", Matchers.equalTo("iPhone 15"))
                    .body("description", Matchers.equalTo("iPhone 15 is a smartphone from Apple"))
                    .body("price", Matchers.equalTo(1000));
    }
}

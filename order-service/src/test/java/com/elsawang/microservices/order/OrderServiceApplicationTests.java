package com.elsawang.microservices.order;

import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.elsawang.microservices.order.stub.InventoryClientStub;

import io.restassured.RestAssured;

/**
 * Integration tests for the Order Service application.
 * Uses TestContainers for MySQL database and WireMock for mocking external services.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0) // Configures WireMock with random port
@Testcontainers // Enables Testcontainers integration
class OrderServiceApplicationTests {

    // MySQL container configuration using TestContainers
    @Container
    @ServiceConnection
    static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.4.0")
            .withDatabaseName("order_db")
            .withUsername("test")
            .withPassword("test");

    @LocalServerPort
    private Integer port;

    private static final String BASE_URI = "http://localhost";
    private static final String ORDER_ENDPOINT = "/api/order";
    private static final String CONTENT_TYPE = "application/json";

    @BeforeEach
    void setup() {
        // Configure RestAssured base URI and port
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }

    @Test
    void shouldSubmitOrder() {
        // Test data for order submission
        String submitOrderJson = """
                {
                    "skuCode": "iPhone_15",
                    "price": 1000,
                    "quantity": 1
                }
                """;

        // Stub inventory service response
        InventoryClientStub.stubInventoryCall("iphone_15", 1);

        // Send POST request and verify response
        String responseBodyString = RestAssured
                .given()
                    .contentType(CONTENT_TYPE)
                    .body(submitOrderJson)
                .when()
                    .post(ORDER_ENDPOINT)
                .then()
                    .log().all()
                    .statusCode(201)
                    .extract()
                    .body()
                    .asString();

        // Verify response message
        assertThat(responseBodyString, Matchers.is("Order Placed Successfully!"));
    }
}

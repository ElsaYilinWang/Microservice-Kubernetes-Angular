package com.elsawang.microservices.order.stub;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

/**
 * Stub class for mocking inventory service calls in tests.
 * Uses WireMock to simulate inventory availability checks.
 */
public class InventoryClientStub {

    private static final String INVENTORY_CHECK_PATH = "/api/inventory/check";
    private static final String CONTENT_TYPE = "application/json";
    private static final int MAX_STOCK_QUANTITY = 100;

    /**
     * Stubs the inventory service call with a mock response based on quantity.
     * Returns true for quantities <= 100, false for larger quantities.
     *
     * @param skuCode Product SKU code to check
     * @param quantity Requested quantity
     */
    public static void stubInventoryCall(String skuCode, Integer quantity) {
        // Build URL with query parameters
        String url = String.format("%s?skuCode=%s&quantity=%d", 
                                 INVENTORY_CHECK_PATH, skuCode, quantity);
        
        // Create response based on quantity threshold
        boolean isAvailable = quantity <= MAX_STOCK_QUANTITY;
        
        stubFor(get(urlEqualTo(url))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", CONTENT_TYPE)
                        .withBody(String.valueOf(isAvailable))));
    }
}

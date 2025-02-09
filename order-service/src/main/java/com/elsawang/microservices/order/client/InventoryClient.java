package com.elsawang.microservices.order.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

/**
 * Client interface for interacting with the Inventory Service.
 * Implements fault tolerance patterns using Resilience4j.
 *
 * Optimizations:
 * - Removed redundant @Slf4j annotation since we're manually creating logger
 * - Added proper endpoint path to match inventory service controller
 * - Added documentation for methods and fault tolerance strategies
 * - Improved parameter naming in fallback method
 */
public interface InventoryClient {

    Logger log = LoggerFactory.getLogger(InventoryClient.class);

    /**
     * Checks if a product is in stock with sufficient quantity.
     * Protected by circuit breaker and retry patterns for fault tolerance.
     *
     * @param skuCode Product SKU code to check
     * @param quantity Required quantity
     * @return true if product is available in sufficient quantity
     */
    @GetExchange("/api/inventory/check")
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @Retry(name = "inventory")
    boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);

    /**
     * Fallback method called when inventory service is unavailable.
     * Returns false to fail safely rather than throw exception.
     *
     * @param skuCode Product SKU code that was being checked
     * @param quantity Quantity that was being verified
     * @param throwable The exception that triggered the fallback
     * @return false to indicate inventory check failed
     */
    default boolean fallbackMethod(String skuCode, Integer quantity, Throwable throwable) {
        log.info("Cannot get inventory for skuCode {}, failure reason: {}", skuCode, throwable.getMessage());
        return false;
    }

}

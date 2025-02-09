package com.elsawang.microservices.order.dto;

import java.math.BigDecimal;

/**
 * Record class representing an order request DTO (Data Transfer Object).
 * Uses Java Record feature for immutable data transfer with built-in equals, hashCode, and toString.
 *
 * @param id Order identifier
 * @param orderNumber Unique order reference number
 * @param skuCode Product SKU code
 * @param price Order price
 * @param quantity Order quantity
 * @param userDetails Customer details associated with the order
 */
public record OrderRequest(
        Long id,
        String orderNumber,
        String skuCode,
        BigDecimal price,
        Integer quantity,
        UserDetails userDetails
) {
    /**
     * Nested record representing customer details associated with an order.
     * Also immutable with built-in equals, hashCode, and toString.
     *
     * @param email Customer's email address
     * @param firstName Customer's first name
     * @param lastName Customer's last name
     */
    public record UserDetails(
            String email,
            String firstName,
            String lastName
    ) {}
}

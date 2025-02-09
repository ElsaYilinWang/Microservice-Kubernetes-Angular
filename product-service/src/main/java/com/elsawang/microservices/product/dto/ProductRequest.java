package com.elsawang.microservices.product.dto;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) for Product requests
 * Uses Java Record for immutable data structure with built-in methods
 * 
 * @param id Product identifier (optional for creation requests)
 * @param name Product name
 * @param description Detailed product description
 * @param price Product price in BigDecimal for precise monetary calculations
 */
public record ProductRequest(
    String id,
    String name, 
    String description,
    BigDecimal price
) {
    /**
     * Compact constructor to validate the required fields
     * Ensures data integrity before object creation
     */
    public ProductRequest {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must be non-null and non-negative");
        }
    }
}

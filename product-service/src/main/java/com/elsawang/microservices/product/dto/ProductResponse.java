package com.elsawang.microservices.product.dto;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) for Product responses
 * Uses Java Record for immutable data structure with built-in methods
 * Provides a lightweight way to transfer product data to clients
 *
 * @param id Unique identifier of the product
 * @param name Name of the product
 * @param description Detailed description of the product
 * @param price Price of the product in BigDecimal for precise monetary values
 */
public record ProductResponse(
    String id,
    String name,
    String description, 
    BigDecimal price
) {
    /**
     * Compact constructor to ensure data consistency
     * Trims string fields to remove unnecessary whitespace
     */
    public ProductResponse {
        name = name != null ? name.trim() : null;
        description = description != null ? description.trim() : null;
    }
}

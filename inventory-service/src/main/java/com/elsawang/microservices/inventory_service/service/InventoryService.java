package com.elsawang.microservices.inventory_service.service;

import com.elsawang.microservices.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * Service class handling inventory-related business logic.
 * Provides methods to check product availability.
 */
@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    /**
     * Checks if a product is available in sufficient quantity.
     * 
     * Optimizations:
     * - Uses @Transactional(readOnly=true) to avoid dirty checking
     * - Leverages optimized repository query that uses COUNT
     * - Validates input parameters
     *
     * @param skuCode Product SKU code to check, must not be null
     * @param quantity Required quantity, must be positive
     * @return true if product exists with sufficient stock
     * @throws IllegalArgumentException if parameters are invalid
     */
    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode, Integer quantity) {
        Assert.hasText(skuCode, "SKU code must not be empty");
        Assert.notNull(quantity, "Quantity must not be null");
        Assert.isTrue(quantity > 0, "Quantity must be positive");

        return inventoryRepository.existsBySkuCodeAndQuantityIsGreaterThanEqual(skuCode, quantity);
    }
}

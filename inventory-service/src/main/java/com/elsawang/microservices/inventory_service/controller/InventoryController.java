package com.elsawang.microservices.inventory_service.controller;

import com.elsawang.microservices.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling inventory-related requests.
 * Provides endpoints to check product availability.
 */
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    /**
     * Checks if a product is in stock with sufficient quantity.
     *
     * @param skuCode  The product's SKU code to check, passed in request body
     * @param quantity The required quantity, passed as query parameter
     * @return true if product exists and has sufficient quantity, false otherwise
     */
    @GetMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        return inventoryService.isInStock(skuCode, quantity);
    }
}

package com.elsawang.microservices.inventory_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elsawang.microservices.inventory_service.model.Inventory;

/**
 * Repository interface for managing Inventory entities.
 * Extends JpaRepository to inherit basic CRUD operations.
 */
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    /**
     * Checks if a product with given SKU code has sufficient quantity in stock.
     * Optimized using @Query to generate more efficient SQL.
     *
     * @param skuCode The product's SKU code to check
     * @param quantity The minimum quantity required
     * @return true if the product exists and has sufficient quantity, false otherwise
     */
    @Query("SELECT COUNT(i) > 0 FROM Inventory i WHERE i.skuCode = :skuCode AND i.quantity >= :quantity")
    boolean existsBySkuCodeAndQuantityIsGreaterThanEqual(@Param("skuCode") String skuCode, @Param("quantity") int quantity);

}

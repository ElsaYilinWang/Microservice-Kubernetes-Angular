package com.elsawang.microservices.inventory_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing inventory records in the system.
 * Maps to the t_inventory table in the database.
 */
@Entity
@Table(name = "t_inventory")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {

    /**
     * Unique identifier for the inventory record.
     * Auto-generated using identity strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Stock Keeping Unit code - unique product identifier.
     * Maps to sku_code column with unique constraint.
     */
    @Column(name = "sku_code", nullable = false, unique = true)
    private String skuCode;

    /**
     * Current quantity in stock.
     * Cannot be null and must be non-negative.
     */
    @Column(nullable = false)
    private Integer quantity = 0;
}

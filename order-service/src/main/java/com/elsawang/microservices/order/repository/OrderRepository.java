package com.elsawang.microservices.order.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.elsawang.microservices.order.model.Order;

/**
 * Repository interface for Order entity that provides CRUD operations and custom queries.
 * Extends JpaRepository to inherit basic CRUD operations and pagination support.
 * The type parameters specify:
 * - Order: the entity type being managed
 * - Long: the type of the entity's primary key
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Find order by order number
    Order findByOrderNumber(String orderNumber);
    
    // Find orders by SKU code
    List<Order> findBySkuCode(String skuCode);
    
    // Find orders created after a specific date
    @Query("SELECT o FROM Order o WHERE o.createdAt > ?1")
    List<Order> findOrdersAfterDate(LocalDateTime date);
    
    // Find orders with quantity greater than specified amount
    List<Order> findByQuantityGreaterThan(Integer quantity);
}

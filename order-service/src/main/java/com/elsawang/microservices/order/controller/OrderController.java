package com.elsawang.microservices.order.controller;

import com.elsawang.microservices.order.dto.OrderRequest;
import com.elsawang.microservices.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for handling order-related HTTP requests.
 * Provides endpoints for order management operations.
 */
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Handles POST requests to create a new order.
     * 
     * @param orderRequest The order details provided in the request body
     * @return ResponseEntity containing confirmation message and HTTP status
     */
    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        try {
            orderService.placeOrder(orderRequest);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Order placed successfully!");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to place order: " + e.getMessage());
        }
    }
}

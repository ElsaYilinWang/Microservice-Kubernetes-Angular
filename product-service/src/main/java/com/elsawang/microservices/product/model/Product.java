package com.elsawang.microservices.product.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Product entity class representing products in the e-commerce system
 * Uses MongoDB as the database with "product" collection
 * Lombok annotations reduce boilerplate code
 */
@Document(value = "product") // Maps class to MongoDB collection named "product"
@AllArgsConstructor // Generates constructor with all arguments
@NoArgsConstructor  // Generates no-args constructor required by MongoDB
@Builder // Enables builder pattern for object creation
@Data   // Generates getters, setters, toString, equals, and hashCode
public class Product {
    @Id // Marks field as the document identifier
    private String id;
    
    @Indexed // Adds database index for faster queries
    private String name;
    
    private String description;
    
    @Indexed // Adds index for price-based queries and sorting
    private BigDecimal price;
}

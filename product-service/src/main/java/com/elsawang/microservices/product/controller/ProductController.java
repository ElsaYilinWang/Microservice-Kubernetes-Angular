package com.elsawang.microservices.product.controller;

import com.elsawang.microservices.product.dto.ProductRequest;
import com.elsawang.microservices.product.dto.ProductResponse;
import com.elsawang.microservices.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for handling Product-related HTTP requests
 * Provides endpoints for creating and retrieving products
 */
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Creates a new product
     * @param productRequest DTO containing product details
     * @return ProductResponse with created product information
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    /**
     * Retrieves all products
     * @return List of ProductResponse containing all products
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }
}

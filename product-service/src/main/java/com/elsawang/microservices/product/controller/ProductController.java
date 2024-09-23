package com.elsawang.microservices.product.controller;

import com.elsawang.microservices.product.dto.ProductRequest;
import com.elsawang.microservices.product.model.Product;
import com.elsawang.microservices.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody ProductRequest productRequest){
        return productService.createProduct(productRequest);
    }
}

package com.elsawang.microservices.product.service;

import com.elsawang.microservices.product.dto.ProductRequest;
import com.elsawang.microservices.product.model.Product;
import com.elsawang.microservices.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public Product createProduct(ProductRequest productRequest){
    Product product = Product.builder()
            .name(productRequest.name())
            .description(productRequest.description())
            .price(productRequest.price())
            .build();
    productRepository.save(product);
    log.info("Product created successfully");
    return product;
    }
}

package com.elsawang.microservices.order.controller;

import com.elsawang.microservices.order.dto.OrderRequest;
import com.elsawang.microservices.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
           orderService.placeOrder(orderRequest);
           return "Order placed Successfully!";
    }
}

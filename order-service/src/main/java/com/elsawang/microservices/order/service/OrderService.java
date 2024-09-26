package com.elsawang.microservices.order.service;

import com.elsawang.microservices.order.client.InventoryClient;
import com.elsawang.microservices.order.dto.OrderRequest;
import com.elsawang.microservices.order.event.OrderPlacedEvent;
import com.elsawang.microservices.order.model.Order;
import com.elsawang.microservices.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest) {
        boolean inStock = inventoryClient.isInStock(
                orderRequest.skuCode(),
                orderRequest.quantity());
        if (inStock) {
            var order = mapToOrder(orderRequest);
            orderRepository.save(order);
            // send the message to Kafka Topic
            var orderPlacedEvent = new OrderPlacedEvent(
                    order.getOrderNumber(),
                    orderRequest.userDetails().email(),
                    orderRequest.userDetails().firstName(),
                    orderRequest.userDetails().lastName());

            log.info("Start- Sending OrderPlacedEvent {} to Kafka Topic", orderPlacedEvent);
            kafkaTemplate.send("order-placed", orderPlacedEvent);
            log.info("End- Sending OrderPlacedEvent {} to Kafka Topic", orderPlacedEvent);
        } else {
            throw new RuntimeException("Product with Skucode " + orderRequest.skuCode() + "is not in stock");
        }
    }

    private static Order mapToOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setQuantity(orderRequest.quantity());
        order.setSkuCode(orderRequest.skuCode());
        return order;
    }
}
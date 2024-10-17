package com.dhuapaya.ms_orders.service;

import com.dhuapaya.ms_orders.entity.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderService {
    Order createOrUpdateOrder(Order order);

    Order getOrderById(UUID id);

    Optional<Order> findOrderByCustomerAndProduct(String customerId, String product);
}

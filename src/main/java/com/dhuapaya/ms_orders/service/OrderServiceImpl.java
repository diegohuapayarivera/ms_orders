package com.dhuapaya.ms_orders.service;

import com.dhuapaya.ms_orders.entity.Order;
import com.dhuapaya.ms_orders.exception.OrderNotFoundException;
import com.dhuapaya.ms_orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createOrUpdateOrder(Order newOrder) {
        Optional<Order> existingOrder = findOrderByCustomerAndProduct(newOrder.getCustomerId(), newOrder.getProduct());

        return existingOrder.map(order -> updateOrderQuantity(order, newOrder.getQuantity())).orElseGet(() -> createNewOrder(newOrder));
    }

    @Override
    public Order getOrderById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    public Optional<Order> findOrderByCustomerAndProduct(String customerId, String product) {
        return orderRepository.findByCustomerIdAndProduct(customerId, product);
    }

    // Métodos privados para dividir la lógica
    private Order updateOrderQuantity(Order existingOrder, int additionalQuantity) {
        existingOrder.setQuantity(existingOrder.getQuantity() + additionalQuantity);
        return orderRepository.save(existingOrder);
    }

    private Order createNewOrder(Order order) {
        return orderRepository.save(order);
    }
}

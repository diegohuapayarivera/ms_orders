package com.dhuapaya.ms_orders.repository;

import com.dhuapaya.ms_orders.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Optional<Order> findByCustomerIdAndProduct(String customerId, String product);

}

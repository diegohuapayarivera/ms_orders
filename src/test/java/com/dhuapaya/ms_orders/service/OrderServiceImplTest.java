package com.dhuapaya.ms_orders.service;

import com.dhuapaya.ms_orders.entity.Order;
import com.dhuapaya.ms_orders.exception.OrderNotFoundException;
import com.dhuapaya.ms_orders.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order sampleOrder;

    @BeforeEach
    void setUp() {
        sampleOrder = Order.builder()
                .customerId("12345")
                .product("Laptop")
                .quantity(2)
                .build();
    }
    @Test
    void testUpdateOrderIfExists() {
        when(orderRepository.findByCustomerIdAndProduct(anyString(), anyString()))
                .thenReturn(Optional.of(sampleOrder));

        orderService.createOrUpdateOrder(
                Order.builder().customerId("12345").product("Laptop").quantity(3).build());

        assertEquals(5, sampleOrder.getQuantity());
        verify(orderRepository, times(1)).save(sampleOrder);
    }

    @Test
    void testGetOrderByIdWhenOrderExists() {
        UUID orderId = UUID.randomUUID();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(sampleOrder));

        Order foundOrder = orderService.getOrderById(orderId);

        assertEquals(sampleOrder, foundOrder);
    }

    @Test
    void testGetOrderByIdWhenOrderDoesNotExist() {
        UUID orderId = UUID.randomUUID();
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(orderId));
    }
}
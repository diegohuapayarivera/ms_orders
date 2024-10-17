package com.dhuapaya.ms_orders.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import com.dhuapaya.ms_orders.entity.Order;
import com.dhuapaya.ms_orders.exception.OrderNotFoundException;
import com.dhuapaya.ms_orders.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.UUID;

@WebMvcTest(OrderController.class)
@Import(OrderService.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private Order sampleOrder;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        sampleOrder = Order.builder()
                .customerId("12345")
                .product("Laptop")
                .quantity(2)
                .build();
    }

    @Test
    void testCreateOrder() throws Exception {
        when(orderService.createOrUpdateOrder(any(Order.class))).thenReturn(sampleOrder);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleOrder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("12345"))
                .andExpect(jsonPath("$.product").value("Laptop"))
                .andExpect(jsonPath("$.quantity").value(2));

        verify(orderService, times(1)).createOrUpdateOrder(any(Order.class));
    }

    @Test
    void testGetOrderById() throws Exception {
        UUID orderId = UUID.randomUUID();
        when(orderService.getOrderById(orderId)).thenReturn(sampleOrder);

        mockMvc.perform(get("/orders/{id}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("12345"))
                .andExpect(jsonPath("$.product").value("Laptop"))
                .andExpect(jsonPath("$.quantity").value(2));

        verify(orderService, times(1)).getOrderById(orderId);
    }


    @Test
    void testGetOrderByIdNotFound() throws Exception {
        UUID orderId = UUID.randomUUID();
        when(orderService.getOrderById(orderId)).thenThrow(new OrderNotFoundException(orderId));

        mockMvc.perform(get("/orders/{id}", orderId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Order con el ID " + orderId + " no se encontro"));

        verify(orderService, times(1)).getOrderById(orderId);
    }
}
package com.dhuapaya.ms_orders.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    private String customerId;
    private String product;
    private int quantity;

    @Builder // Permite crear objetos con un patrón Builder
    public Order(String customerId, String product, int quantity) {
        this.id = UUID.randomUUID(); // Genera UUID automáticamente
        this.customerId = customerId;
        this.product = product;
        this.quantity = quantity;
    }
}

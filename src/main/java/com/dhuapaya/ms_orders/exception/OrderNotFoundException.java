package com.dhuapaya.ms_orders.exception;

import java.util.UUID;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(UUID id) {
        super("Order con el ID " + id + " no se encontro");
    }
}

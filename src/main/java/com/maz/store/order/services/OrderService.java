package com.maz.store.order.services;

import com.maz.store.model.order.OrderDto;

import java.util.UUID;

public interface OrderService {

    UUID createOrder(OrderDto order);

    OrderDto checkOrderStatus(UUID orderId);

    OrderDto cancelOrder(UUID orderId);
}

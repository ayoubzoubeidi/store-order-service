package com.maz.store.order.services;

import com.maz.store.model.order.OrderDto;
import com.maz.store.order.domain.BaseOrder;
import com.maz.store.order.repositories.OrderRepository;
import com.maz.store.order.sm.StateMachineManager;
import com.maz.store.order.web.exceptions.OrderNotFoundException;
import com.maz.store.order.web.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final StateMachineManager stateMachineManager;
    private final OrderMapper orderMapper;

    @Override
    public UUID createOrder(OrderDto order) {

        BaseOrder baseOrder = orderMapper.orderDtoToOrder(order);

        BaseOrder savedOrder = stateMachineManager.createOrder(baseOrder);

        return savedOrder.getId();

    }

    @Override
    public OrderDto checkOrderStatus(UUID orderId) {

        BaseOrder order = orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("Order Not Found id = " + orderId)
        );

        return orderMapper.orderToOrderDto(order);
    }


    @Override
    public OrderDto cancelOrder(UUID orderId) {
        stateMachineManager.cancelOrder(orderId);
        BaseOrder order = orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("Order Not Found id = " + orderId)
        );
        return orderMapper.orderToOrderDto(order);
    }
}

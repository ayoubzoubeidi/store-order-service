package com.maz.store.order.services;

import com.maz.store.model.order.OrderDto;
import com.maz.store.order.domain.BaseOrder;
import com.maz.store.order.repositories.OrderRepository;
import com.maz.store.order.sm.StateMachineManager;
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

        Optional<BaseOrder> orderOptional = orderRepository.findById(orderId);

        if (orderOptional.isEmpty())
            throw new RuntimeException("Order Not Found");
        else {

            return orderMapper.orderToOrderDto(orderOptional.get());
        }

    }
}

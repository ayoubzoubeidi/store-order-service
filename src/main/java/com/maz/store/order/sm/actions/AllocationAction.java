package com.maz.store.order.sm.actions;

import com.maz.store.model.inventory.AllocationRequest;
import com.maz.store.order.domain.OrderStatus;
import com.maz.store.order.repositories.OrderRepository;
import com.maz.store.order.sm.OrderEvent;
import com.maz.store.order.web.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

import static com.maz.store.order.config.JmsConfig.ALLOCATE_ORDER_QUEUE;
import static com.maz.store.order.sm.StateMachineManagerImpl.ORDER_ID_HEADER;

@Component
@RequiredArgsConstructor
@Slf4j
public class AllocationAction implements Action<OrderStatus, OrderEvent> {

    private final JmsTemplate jmsTemplate;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public void execute(StateContext<OrderStatus, OrderEvent> stateContext) {


        UUID orderId = UUID.fromString(
                (String) Objects.requireNonNull(stateContext.getMessage().getHeaders().get(ORDER_ID_HEADER))
        );

        orderRepository.findById(orderId).ifPresentOrElse(
                order -> {
                    jmsTemplate.convertAndSend(ALLOCATE_ORDER_QUEUE,
                            AllocationRequest.builder().orderDto(orderMapper.orderToOrderDto(order)).build()
                            );
                }
        , () -> log.error("Order Not Found " + orderId));

    }
}

package com.maz.store.order.sm.actions;

import com.maz.store.model.order.OrderDto;
import com.maz.store.model.product.ValidationRequest;
import com.maz.store.model.product.ValidationResponse;
import com.maz.store.order.config.JmsConfig;
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

import java.util.UUID;

import static com.maz.store.order.sm.StateMachineManagerImpl.ORDER_ID_HEADER;

@Component
@RequiredArgsConstructor
@Slf4j
public class ValidateOrderAction implements Action<OrderStatus, OrderEvent> {

    private final JmsTemplate jmsTemplate;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public void execute(StateContext<OrderStatus, OrderEvent> stateContext) {
        String orderId = (String) stateContext.getMessage().getHeaders().get(ORDER_ID_HEADER);

        orderRepository.findById(UUID.fromString(orderId)).ifPresentOrElse(
                baseOrder -> {
                    jmsTemplate.convertAndSend(
                            JmsConfig.VALIDATE_ORDER_QUEUE,
                            ValidationRequest.builder().order(orderMapper.orderToOrderDto(baseOrder)).build());
                    log.info("Validation request sent with order id " + orderId);
                }, () -> log.error("Order Not Found " + orderId)
        );

    }
}

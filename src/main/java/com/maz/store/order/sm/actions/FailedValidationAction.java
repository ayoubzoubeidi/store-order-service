package com.maz.store.order.sm.actions;

import com.maz.store.order.domain.OrderStatus;
import com.maz.store.order.repositories.OrderRepository;
import com.maz.store.order.sm.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import static com.maz.store.order.sm.StateMachineManagerImpl.ORDER_ID_HEADER;

@Component
@RequiredArgsConstructor
@Slf4j
public class FailedValidationAction implements Action<OrderStatus, OrderEvent> {

    private final OrderRepository orderRepository;

    @Override
    public void execute(StateContext<OrderStatus, OrderEvent> stateContext) {

        String orderId = (String) stateContext.getMessage().getHeaders().get(ORDER_ID_HEADER);

        log.info("Order Validation Error " + orderId);

    }
}

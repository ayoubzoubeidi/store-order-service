package com.maz.store.order.sm.actions;

import com.maz.store.order.domain.OrderStatus;
import com.maz.store.order.sm.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.maz.store.order.sm.StateMachineManagerImpl.ORDER_ID_HEADER;

@Component
@Slf4j
public class CancellationAction implements Action<OrderStatus, OrderEvent> {



    @Override
    public void execute(StateContext<OrderStatus, OrderEvent> stateContext) {
        String orderId =
                (String) Objects.requireNonNull(stateContext.getMessage().getHeaders().get(ORDER_ID_HEADER));

        log.debug("Order Cancelled " + orderId);
    }
}

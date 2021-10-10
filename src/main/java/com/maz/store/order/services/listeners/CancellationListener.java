package com.maz.store.order.services.listeners;

import com.maz.store.model.cancellation.CancelOrder;
import com.maz.store.order.sm.StateMachineManager;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static com.maz.store.order.config.JmsConfig.CANCEL_QUEUE;

@Component
@RequiredArgsConstructor
public class CancellationListener {

    private final StateMachineManager stateMachineManager;

    @JmsListener(destination = CANCEL_QUEUE)
    public void listen(CancelOrder cancelOrder) {
        stateMachineManager.cancelOrder(cancelOrder.getOrderId());
    }
}

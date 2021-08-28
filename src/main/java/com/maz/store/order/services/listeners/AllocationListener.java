package com.maz.store.order.services.listeners;

import com.maz.store.model.inventory.AllocationResponse;
import com.maz.store.order.sm.StateMachineManager;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import static com.maz.store.order.config.JmsConfig.ALLOCATION_RESPONSE_QUEUE;

@Service
@RequiredArgsConstructor
public class AllocationListener {

    private final StateMachineManager stateMachineManager;

    @JmsListener(destination = ALLOCATION_RESPONSE_QUEUE)
    public void listen(AllocationResponse response) {

        if (!response.getAllocationError()) {
            stateMachineManager.allocateOrder(response.getOrder());
        } else {
            stateMachineManager.failedAllocation(response.getOrder().getId());
        }

    }

}

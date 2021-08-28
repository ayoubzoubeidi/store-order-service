package com.maz.store.order.sm.listeners;


import com.maz.store.model.inventory.AllocationRequest;
import com.maz.store.model.inventory.AllocationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import static com.maz.store.order.config.JmsConfig.ALLOCATE_ORDER_QUEUE;
import static com.maz.store.order.config.JmsConfig.ALLOCATION_RESPONSE_QUEUE;
import static com.maz.store.order.sm.StateMachineManagerTest.FAILED_ALLOCATION_ID;

@Component
@RequiredArgsConstructor
public class AllocationL {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = ALLOCATE_ORDER_QUEUE)
    public void listen(AllocationRequest request) {

        Boolean allocationError = request.getOrderDto().getCustomerId().equals(FAILED_ALLOCATION_ID);

        request.getOrderDto().getOrderLines().forEach(
                line -> {
                    line.setQuantityAllocated(line.getOrderQuantity());
                }
        );

        jmsTemplate.convertAndSend(
                ALLOCATION_RESPONSE_QUEUE,
                AllocationResponse.builder().order(request.getOrderDto()).allocationError(allocationError).partialAllocation(false)
                        .build()
        );

    }

}

package com.maz.store.order.sm.listeners;


import com.maz.store.model.inventory.AllocationRequest;
import com.maz.store.model.inventory.AllocationResponse;
import com.maz.store.model.inventory.DeAllocationRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import static com.maz.store.order.config.JmsConfig.*;
import static com.maz.store.order.sm.StateMachineManagerTest.FAILED_ALLOCATION_ID;

@Component
@RequiredArgsConstructor
public class AllocationMockListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = ALLOCATE_ORDER_QUEUE)
    @SneakyThrows
    public void listen(AllocationRequest request) {

        Thread.sleep(1000);

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

    @JmsListener(destination = DE_ALLOCATION_QUEUE)
    public void listenDeAllocation(DeAllocationRequest request) {
    }
}

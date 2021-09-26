package com.maz.store.order.sm.listeners;

import com.maz.store.model.product.ValidationRequest;
import com.maz.store.model.product.ValidationResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import static com.maz.store.order.config.JmsConfig.VALIDATE_ORDER_QUEUE;
import static com.maz.store.order.config.JmsConfig.VALIDATION_RESPONSE_QUEUE;
import static com.maz.store.order.sm.StateMachineManagerTest.FAILED_VALIDATION_ID;
import static com.maz.store.order.sm.StateMachineManagerTest.STOP_AT_VALIDATION;

@Component
@RequiredArgsConstructor
public class ValidationMockListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = VALIDATE_ORDER_QUEUE)
    @SneakyThrows
    public void listen(ValidationRequest request) {

        Thread.sleep(1000);


        Boolean isValid = !request.getOrder().getCustomerId().equals(FAILED_VALIDATION_ID);

        if (!request.getOrder().getOrderLines().get(0).getUpc().equals(STOP_AT_VALIDATION))
        jmsTemplate.convertAndSend(VALIDATION_RESPONSE_QUEUE, ValidationResponse.builder().orderId(request.getOrder().getId()).isValid(isValid).build());

    }
}

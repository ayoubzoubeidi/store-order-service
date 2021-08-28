package com.maz.store.order.sm.listeners;

import com.maz.store.model.product.ValidationRequest;
import com.maz.store.model.product.ValidationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import static com.maz.store.order.config.JmsConfig.VALIDATE_ORDER_QUEUE;
import static com.maz.store.order.config.JmsConfig.VALIDATION_RESPONSE_QUEUE;
import static com.maz.store.order.sm.StateMachineManagerTest.FAILED_VALIDATION_ID;

@Component
@RequiredArgsConstructor
public class ValidationL {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = VALIDATE_ORDER_QUEUE)
    public void listen(ValidationRequest request) {

        Boolean isValid = !request.getOrder().getCustomerId().equals(FAILED_VALIDATION_ID);

        jmsTemplate.convertAndSend(VALIDATION_RESPONSE_QUEUE, ValidationResponse.builder().orderId(request.getOrder().getId()).isValid(isValid).build());

    }
}

package com.maz.store.order.services.listeners;

import com.maz.store.model.product.ValidationResponse;
import com.maz.store.order.config.JmsConfig;
import com.maz.store.order.sm.StateMachineManager;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidationListener {

    private final StateMachineManager stateMachineManager;

    @JmsListener(destination = JmsConfig.VALIDATION_RESPONSE_QUEUE)
    public void validationResponseListener(ValidationResponse validationResponse) {
        if(validationResponse.isValid())
            stateMachineManager.validateOrder(validationResponse.getOrderId());
    }

}

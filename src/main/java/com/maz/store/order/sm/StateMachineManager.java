package com.maz.store.order.sm;

import com.maz.store.order.domain.BaseOrder;

import java.util.UUID;

public interface StateMachineManager {

    BaseOrder createOrder(BaseOrder baseOrder);

    void validateOrder(UUID orderId);

    void allocateOrder(UUID orderId);

}

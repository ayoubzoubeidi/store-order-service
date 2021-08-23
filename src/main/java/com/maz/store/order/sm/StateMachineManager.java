package com.maz.store.order.sm;

import com.maz.store.order.domain.BaseOrder;

public interface StateMachineManager {

    BaseOrder createOrder(BaseOrder baseOrder);

    void validateOrder();

    void allocateOrder();

}

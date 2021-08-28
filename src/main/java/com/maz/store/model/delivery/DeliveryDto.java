package com.maz.store.model.delivery;

import com.maz.store.model.order.OrderDto;
import com.maz.store.model.customer.CustomerDto;

import java.io.Serializable;

public class DeliveryDto implements Serializable {

    private static final long serialVersionUID = -975568898983791865L;

    private CustomerDto user;

    private OrderDto order;

}

package com.maz.store.model.delivery;

import com.maz.store.model.order.OrderDto;
import com.maz.store.model.customer.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryRequest implements Serializable {

    private static final long serialVersionUID = 6476204537437591479L;

    OrderDto order;

    CustomerDto customerDto;

}

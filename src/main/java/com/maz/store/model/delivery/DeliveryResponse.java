package com.maz.store.model.delivery;

import com.maz.store.model.order.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryResponse implements Serializable {

    private static final long serialVersionUID = 8819316071110446335L;

    private OrderDto order;

    private Boolean deliveryError;

    private Date ETA;

}

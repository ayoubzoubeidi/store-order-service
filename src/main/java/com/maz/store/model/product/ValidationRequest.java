package com.maz.store.model.product;

import com.maz.store.model.order.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidationRequest implements Serializable {

    static final long serialVersionUID = 7282052642679878018L;

    private OrderDto order;

}

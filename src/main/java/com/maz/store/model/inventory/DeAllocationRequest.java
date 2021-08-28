package com.maz.store.model.inventory;

import com.maz.store.model.order.OrderDto;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class DeAllocationRequest implements Serializable {

    static final long serialVersionUID = -5381494460495804048L;

    private OrderDto orderDto;

}

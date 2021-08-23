package com.maz.store.model.inventory;

import com.maz.store.model.order.OrderDto;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
public class AllocationRequest implements Serializable {
    
    static final long serialVersionUID = 2047999780720956077L;
    private OrderDto orderDto;

}

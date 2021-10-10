package com.maz.store.model.inventory;

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
public class AllocationRequest implements Serializable {
    
    static final long serialVersionUID = 2047999780720956077L;
    private OrderDto orderDto;

}

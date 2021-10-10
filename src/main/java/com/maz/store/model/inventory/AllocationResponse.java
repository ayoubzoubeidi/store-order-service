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
public class AllocationResponse implements Serializable {

    static final long serialVersionUID = 4984002496473043204L;

    private OrderDto order;

    private Boolean partialAllocation;

    private Boolean allocationError;

}

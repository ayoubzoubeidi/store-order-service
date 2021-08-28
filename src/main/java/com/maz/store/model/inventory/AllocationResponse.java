package com.maz.store.model.inventory;

import com.maz.store.model.order.OrderDto;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

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

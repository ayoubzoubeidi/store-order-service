package com.maz.store.model.inventory;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class AllocationResponse implements Serializable {

    static final long serialVersionUID = 4984002496473043204L;
    private UUID orderId;

}

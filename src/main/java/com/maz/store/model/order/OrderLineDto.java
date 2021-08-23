package com.maz.store.model.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderLineDto {

    private UUID id;
    private String upc;
    private UUID productId;
    private Integer orderQuantity = 0;
    private Integer quantityAllocated = 0;
    private BigDecimal price;

}

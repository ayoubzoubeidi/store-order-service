package com.maz.store.model.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto implements Serializable {
    static final long serialVersionUID = 2287771368774120893L;
    private UUID id;
    private UUID customerId;
    private List<OrderLineDto> orderLines = new ArrayList<>();
    private String status;


}

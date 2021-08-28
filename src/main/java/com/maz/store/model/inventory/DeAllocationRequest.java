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
public class DeAllocationRequest implements Serializable {

    static final long serialVersionUID = -5381494460495804048L;

    private OrderDto orderDto;

}

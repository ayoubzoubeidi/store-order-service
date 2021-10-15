package com.maz.store.order.web.mappers;

import com.maz.store.model.order.OrderLineDto;
import com.maz.store.order.domain.OrderLine;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrderLineMapper {

    @Mapping(source = "id", target = "productId")
    OrderLineDto orderLineToOrderLineDto(OrderLine orderLine);

    @InheritInverseConfiguration
    OrderLine orderLineDtoToOrderLine(OrderLineDto orderLineDto);

}

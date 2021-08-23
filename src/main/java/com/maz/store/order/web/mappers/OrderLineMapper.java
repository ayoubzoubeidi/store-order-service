package com.maz.store.order.web.mappers;

import com.maz.store.model.order.OrderLineDto;
import com.maz.store.order.domain.OrderLine;
import org.mapstruct.Mapper;

@Mapper
public interface OrderLineMapper {

    OrderLineDto orderLineToOrderLineDto(OrderLine orderLine);

    OrderLine orderLineDtoToOrderLine(OrderLineDto orderLineDto);

}

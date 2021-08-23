package com.maz.store.order.web.mappers;

import com.maz.store.model.order.OrderDto;
import com.maz.store.order.domain.BaseOrder;
import org.mapstruct.Mapper;

@Mapper(uses = {OrderLineMapper.class})
public interface OrderMapper {

    OrderDto orderToOrderDto(BaseOrder baseOrder);

    BaseOrder orderDtoToOrder(OrderDto orderDto);

}

package com.maz.store.order.sm;

import com.maz.store.model.order.OrderDto;
import com.maz.store.order.domain.BaseOrder;
import com.maz.store.order.domain.OrderStatus;
import com.maz.store.order.repositories.OrderRepository;
import com.maz.store.order.sm.interceptors.PreStateChangeInterceptor;
import com.maz.store.order.web.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.UUID;

import static com.maz.store.order.domain.OrderStatus.DE_ALLOCATION_PENDING;

@Component
@RequiredArgsConstructor
@Transactional
public class StateMachineManagerImpl implements StateMachineManager {

    public static String ORDER_ID_HEADER = "order-id";
    private final StateMachineFactory<OrderStatus, OrderEvent> stateMachineFactory;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final PreStateChangeInterceptor preStateChangeInterceptor;


    @Override
    public BaseOrder createOrder(BaseOrder baseOrder) {

        baseOrder.setId(null);
        baseOrder.setStatus(OrderStatus.NEW);
        BaseOrder savedBaseOrder = orderRepository.saveAndFlush(baseOrder);
        sendEvent(savedBaseOrder, OrderEvent.VALIDATE);

        return savedBaseOrder;

    }

    @Override
    public void validateOrder(UUID orderId) {
        BaseOrder order = orderRepository.getById(orderId);

        sendEvent(order, OrderEvent.PASS_VALIDATION);

        BaseOrder order1 = orderRepository.getById(orderId);

        sendEvent(order1, OrderEvent.ALLOCATE);

    }

    @Override
    public void allocateOrder(OrderDto order) {

        BaseOrder managedOrder = orderRepository.findById(order.getId()).get();

        // Persist order's quantity allocated.
        order.getOrderLines().forEach(
                (orderLineDto -> {
                    orderRepository.updateOrderLine(orderLineDto.getId(), orderLineDto.getQuantityAllocated());
                })
        );

        if (managedOrder.getStatus() == DE_ALLOCATION_PENDING) {

            sendEvent(managedOrder, OrderEvent.FAIL_ALLOCATION);
            sendEvent(managedOrder, OrderEvent.DE_ALLOCATED);

        } else {

            sendEvent(managedOrder, OrderEvent.PASS_ALLOCATION);

            sendEvent(managedOrder, OrderEvent.DELIVER);
        }

    }

    @Override
    public void failedValidation(UUID orderId) {

        BaseOrder order = orderRepository.getById(orderId);

        sendEvent(order, OrderEvent.FAIL_VALIDATION);

    }

    @Override
    public void failedAllocation(UUID orderId) {

        BaseOrder order = orderRepository.getById(orderId);

        sendEvent(order, OrderEvent.FAIL_ALLOCATION);

    }

    @Override
    public void cancelOrder(UUID orderId) {

        BaseOrder order = orderRepository.getById(orderId);

        sendEvent(order, OrderEvent.CANCEL);

    }

    private void sendEvent(BaseOrder baseOrder, OrderEvent event) {

        StateMachine<OrderStatus, OrderEvent> stateMachine = buildStateMachine(baseOrder);

        Message message = MessageBuilder.withPayload(event).setHeader(ORDER_ID_HEADER, baseOrder.getId().toString()).build();

        stateMachine.sendEvent(message);

    }

    private StateMachine<OrderStatus, OrderEvent> buildStateMachine(BaseOrder baseOrder) {

        StateMachine<OrderStatus, OrderEvent> stateMachine = stateMachineFactory.getStateMachine(baseOrder.getId());
        stateMachine.stop();

        stateMachine.getStateMachineAccessor().doWithAllRegions(
                sma -> {
                    sma.resetStateMachine(new DefaultStateMachineContext<>(baseOrder.getStatus(), null, null, null));
                    sma.addStateMachineInterceptor(preStateChangeInterceptor);
                }
        );

        stateMachine.start();

        return stateMachine;

    }

}

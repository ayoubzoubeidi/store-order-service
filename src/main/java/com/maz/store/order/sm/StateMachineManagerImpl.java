package com.maz.store.order.sm;

import com.maz.store.order.domain.BaseOrder;
import com.maz.store.order.domain.OrderStatus;
import com.maz.store.order.repositories.OrderRepository;
import com.maz.store.order.sm.interceptors.PreStateChangeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class StateMachineManagerImpl implements StateMachineManager {

    public static String ORDER_ID_HEADER = "order-id";
    private final StateMachineFactory<OrderStatus, OrderEvent> stateMachineFactory;
    private final OrderRepository orderRepository;
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
    public void validateOrder() {
    }

    @Override
    public void allocateOrder() {

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
                    sma.resetStateMachine(new DefaultStateMachineContext<OrderStatus, OrderEvent>(baseOrder.getStatus(), null, null, null));
                    sma.addStateMachineInterceptor(preStateChangeInterceptor);
                }
        );

        stateMachine.start();

        return stateMachine;

    }

}
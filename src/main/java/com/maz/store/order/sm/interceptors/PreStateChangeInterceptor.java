package com.maz.store.order.sm.interceptors;

import com.maz.store.order.domain.BaseOrder;
import com.maz.store.order.domain.OrderStatus;
import com.maz.store.order.repositories.OrderRepository;
import com.maz.store.order.sm.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

import static com.maz.store.order.sm.StateMachineManagerImpl.ORDER_ID_HEADER;

@Component
@RequiredArgsConstructor
@Slf4j
public class PreStateChangeInterceptor extends StateMachineInterceptorAdapter<OrderStatus, OrderEvent> {

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public void preStateChange(State<OrderStatus, OrderEvent> state, Message<OrderEvent> message, Transition<OrderStatus, OrderEvent> transition, StateMachine<OrderStatus, OrderEvent> stateMachine, StateMachine<OrderStatus, OrderEvent> rootStateMachine) {
        Optional.ofNullable(message)
                .flatMap(msg -> Optional.ofNullable((String) msg.getHeaders().getOrDefault(ORDER_ID_HEADER, "")))
                .ifPresent(orderId -> {
                    log.debug("Saving state for order id: " + orderId + " status: " + state.getId());
                    BaseOrder baseOrder = orderRepository.getById(UUID.fromString(orderId));
                    baseOrder.setStatus(state.getId());
                    orderRepository.saveAndFlush(baseOrder);
                });

    }
}

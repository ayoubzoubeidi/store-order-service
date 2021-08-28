package com.maz.store.order.sm;

import com.maz.store.order.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
@RequiredArgsConstructor
public class StateMachineConfig extends StateMachineConfigurerAdapter<OrderStatus, OrderEvent> {

    private final Action<OrderStatus, OrderEvent> validateOrderAction;
    private final Action<OrderStatus, OrderEvent> allocationAction;
    private final Action<OrderStatus, OrderEvent> failedValidationAction;
    private final Action<OrderStatus, OrderEvent> deAllocationAction;
    private final Action<OrderStatus, OrderEvent> cancellationAction;


    @Override
    public void configure(StateMachineStateConfigurer<OrderStatus, OrderEvent> states) throws Exception {
        states
                .withStates()
                .initial(OrderStatus.NEW)
                .states(EnumSet.allOf(OrderStatus.class))
                .end(OrderStatus.ALLOCATION_FAILED)
                .end(OrderStatus.VALIDATION_FAILED)
                .end(OrderStatus.CANCELLED)
                .end(OrderStatus.PICKED_UP);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStatus, OrderEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(OrderStatus.NEW).target(OrderStatus.VALIDATION_PENDING).event(OrderEvent.VALIDATE).action(validateOrderAction)
                .and()

                .withExternal()
                .source(OrderStatus.VALIDATION_PENDING).target(OrderStatus.CANCELLED).event(OrderEvent.CANCEL).action(cancellationAction)
                .and()

                .withExternal()
                .source(OrderStatus.VALIDATION_PENDING).target(OrderStatus.VALIDATED).event(OrderEvent.PASS_VALIDATION)
                .and()

                .withExternal()
                .source(OrderStatus.VALIDATION_PENDING).target(OrderStatus.VALIDATION_FAILED).event(OrderEvent.FAIL_VALIDATION).action(failedValidationAction)
                .and()

                .withExternal()
                .source(OrderStatus.VALIDATED).target(OrderStatus.ALLOCATION_PENDING).event(OrderEvent.ALLOCATE).action(allocationAction)
                .and()

                .withExternal()
                .source(OrderStatus.VALIDATED).target(OrderStatus.CANCELLED).event(OrderEvent.CANCEL).action(cancellationAction)
                .and()

                .withExternal()
                .source(OrderStatus.ALLOCATION_PENDING).target(OrderStatus.ALLOCATED).event(OrderEvent.PASS_ALLOCATION)
                .and()

                .withExternal()
                .source(OrderStatus.ALLOCATION_PENDING).target(OrderStatus.DE_ALLOCATION_PENDING).event(OrderEvent.CANCEL)
                .and()

                .withExternal()
                .source(OrderStatus.DE_ALLOCATION_PENDING).target(OrderStatus.CANCELLED).event(OrderEvent.DE_ALLOCATED).action(deAllocationAction).action(cancellationAction)
                .and()

                .withExternal()
                .source(OrderStatus.ALLOCATION_PENDING).target(OrderStatus.ALLOCATION_FAILED).event(OrderEvent.FAIL_ALLOCATION).action(deAllocationAction)
                .and()

                .withExternal()
                .source(OrderStatus.ALLOCATED).target(OrderStatus.DELIVERY_PENDING).event(OrderEvent.DELIVER)
                .and()

                .withExternal()
                .source(OrderStatus.ALLOCATED).target(OrderStatus.CANCELLED).event(OrderEvent.CANCEL).action(deAllocationAction).action(cancellationAction)
                .and()

                .withExternal()
                .source(OrderStatus.DELIVERY_PENDING).target(OrderStatus.PICKED_UP).event(OrderEvent.PICKED_UP)
                .and()

                .withExternal()
                .source(OrderStatus.DELIVERY_PENDING).target(OrderStatus.CANCELLED).event(OrderEvent.CANCEL).action(deAllocationAction).action(cancellationAction)
                .and()

                .withExternal()
                .source(OrderStatus.PICKED_UP).target(OrderStatus.DELIVERED).event(OrderEvent.DELIVERED);

    }

}

package com.maz.order.sm;

import com.maz.order.domain.OrderStatus;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<OrderStatus, OrderEvents> {

    @Override
    public void configure(StateMachineStateConfigurer<OrderStatus, OrderEvents> states) throws Exception {
        states
                .withStates()
                .initial(OrderStatus.NEW)
                .end(OrderStatus.ALLOCATION_FAILED)
                .end(OrderStatus.VALIDATION_FAILED)
                .end(OrderStatus.CANCELLED)
                .end(OrderStatus.PICKED_UP)
                .states(EnumSet.allOf(OrderStatus.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStatus, OrderEvents> transitions) throws Exception {
        transitions
                .withExternal()
                .source(OrderStatus.NEW).target(OrderStatus.VALIDATION_PENDING).event(OrderEvents.VALIDATE)
                .and()
                .withExternal()
                .source(OrderStatus.VALIDATION_PENDING).target(OrderStatus.VALIDATED).event(OrderEvents.PASS_VALIDATION)
                .and()
                .withExternal()
                .source(OrderStatus.VALIDATION_PENDING).target(OrderStatus.VALIDATION_FAILED).event(OrderEvents.FAIL_VALIDATION)
                .and()
                .withExternal()
                .source(OrderStatus.VALIDATED).target(OrderStatus.ALLOCATION_PENDING).event(OrderEvents.ALLOCATE)
                .and()
                .withExternal()
                .source(OrderStatus.ALLOCATION_PENDING).target(OrderStatus.ALLOCATED).event(OrderEvents.PASS_ALLOCATION)
                .and()
                .withExternal()
                .source(OrderStatus.ALLOCATION_PENDING).target(OrderStatus.ALLOCATION_FAILED).event(OrderEvents.FAIL_ALLOCATION)
                .and()
                .withExternal()
                .source(OrderStatus.ALLOCATED).target(OrderStatus.DELIVERY_PENDING).event(OrderEvents.DELIVER)
                .and()
                .withExternal()
                .source(OrderStatus.DELIVERY_PENDING).target(OrderStatus.PICKED_UP).event(OrderEvents.PICKED_UP)
                .and()
                .withExternal()
                .source(OrderStatus.PICKED_UP).target(OrderStatus.DELIVERED).event(OrderEvents.DELIVERED);

    }

}

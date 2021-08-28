package com.maz.store.order.sm;

import com.maz.store.model.cancellation.CancelOrder;
import com.maz.store.order.domain.BaseOrder;
import com.maz.store.order.domain.OrderLine;
import com.maz.store.order.repositories.OrderRepository;
import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.server.ActiveMQServer;
import org.apache.activemq.artemis.core.server.ActiveMQServers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.maz.store.order.config.JmsConfig.CANCEL_QUEUE;
import static com.maz.store.order.domain.OrderStatus.*;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class StateMachineManagerTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    StateMachineManager stateMachineManager;

    @Autowired
    JmsTemplate jmsTemplate;

    public static UUID FAILED_VALIDATION_ID = UUID.fromString("c6e5bc61-0816-11ec-af4d-f8a2d6b4d38b");
    public static UUID FAILED_ALLOCATION_ID = UUID.fromString("19dcf3e3-419b-4830-a577-c44415c539dd");
    public static String STOP_AT_VALIDATION = "STOP AT VALIDATION PENDING";

    @TestConfiguration
    static class messagingConfiguration {
        @Bean
        public void config() throws Exception {
            ActiveMQServer server = ActiveMQServers.newActiveMQServer(new ConfigurationImpl()
                    .setPersistenceEnabled(false)
                    .setJournalDatasync(false)
                    .setSecurityEnabled(false)
                    .addAcceptorConfiguration("invm", "vm://10"));
            server.start();
        }
    }



    @Test
    public void testNewToPendingDeliveryHappyPath() {

        BaseOrder order = setUp();

        BaseOrder managedOrder = stateMachineManager.createOrder(order);

        await().untilAsserted(() -> {
            BaseOrder baseOrder = orderRepository.findById(managedOrder.getId()).get();
            assertEquals(ALLOCATION_PENDING, baseOrder.getStatus());
        });


        await().untilAsserted(() -> {
            BaseOrder baseOrder = orderRepository.findById(managedOrder.getId()).get();
            assertEquals(DELIVERY_PENDING, baseOrder.getStatus());
        });


        BaseOrder baseOrder = orderRepository.findById(managedOrder.getId()).get();
        assertEquals(DELIVERY_PENDING, baseOrder.getStatus());

    }



    @Test
    public void testFailValidation() {

        BaseOrder order = setUp();

        order.setCustomerId(FAILED_VALIDATION_ID);

        BaseOrder managedOrder = stateMachineManager.createOrder(order);

        await().untilAsserted(() -> {
            BaseOrder baseOrder = orderRepository.findById(managedOrder.getId()).get();
            assertEquals(VALIDATION_FAILED, baseOrder.getStatus());
        });

        BaseOrder baseOrder = orderRepository.findById(managedOrder.getId()).get();
        assertEquals(VALIDATION_FAILED, baseOrder.getStatus());

    }


    @Test
    public void testFailAllocation() {

        BaseOrder order = setUp();

        order.setCustomerId(FAILED_ALLOCATION_ID);

        BaseOrder managedOrder = stateMachineManager.createOrder(order);

        await().untilAsserted(() -> {
            BaseOrder baseOrder = orderRepository.findById(managedOrder.getId()).get();
            assertEquals(ALLOCATION_FAILED, baseOrder.getStatus());
        });

        BaseOrder baseOrder = orderRepository.findById(managedOrder.getId()).get();
        assertEquals(ALLOCATION_FAILED, baseOrder.getStatus());

    }

    @Test
    public void testCancellationWithDeAllocation() {

        BaseOrder order = setUp();

        BaseOrder managedOrder = stateMachineManager.createOrder(order);

        await().untilAsserted(() -> {
            BaseOrder baseOrder = orderRepository.findById(managedOrder.getId()).get();
            assertEquals(DELIVERY_PENDING, baseOrder.getStatus());
        });

        jmsTemplate.convertAndSend(CANCEL_QUEUE, CancelOrder.builder().orderId(managedOrder.getId()).build());

        await().untilAsserted(() -> {
            BaseOrder baseOrder = orderRepository.findById(managedOrder.getId()).get();
            assertEquals(CANCELLED, baseOrder.getStatus());
        });

        BaseOrder baseOrder = orderRepository.findById(managedOrder.getId()).get();

        assertEquals(CANCELLED, baseOrder.getStatus());

    }

    @Test
    public void testCancellationWithoutDeAllocation() {

        BaseOrder order = setUp();

        order.getOrderLines().forEach(
                line -> line.setUpc(STOP_AT_VALIDATION)
        );

        BaseOrder managedOrder = stateMachineManager.createOrder(order);

        await().untilAsserted(() -> {
            BaseOrder baseOrder = orderRepository.findById(managedOrder.getId()).get();
            assertEquals(VALIDATION_PENDING, baseOrder.getStatus());
        });

        jmsTemplate.convertAndSend(CANCEL_QUEUE, CancelOrder.builder().orderId(managedOrder.getId()).build());

        await().untilAsserted(() -> {
            BaseOrder baseOrder = orderRepository.findById(managedOrder.getId()).get();
            assertEquals(CANCELLED, baseOrder.getStatus());
        });

        BaseOrder baseOrder = orderRepository.findById(managedOrder.getId()).get();

        assertEquals(CANCELLED, baseOrder.getStatus());

    }


    public BaseOrder setUp() {
        Set<OrderLine> orderLines = new HashSet<>();
        BaseOrder order = BaseOrder.builder().customerId(UUID.randomUUID()).build();
        orderLines.add(
                OrderLine.builder().orderQuantity(20).productId(UUID.randomUUID()).upc("1234").baseOrder(order).build()
        );
        orderLines.add(
                OrderLine.builder().orderQuantity(50).productId(UUID.randomUUID()).upc("1234").baseOrder(order).build()
        );
        orderLines.add(
                OrderLine.builder().orderQuantity(60).productId(UUID.randomUUID()).upc("1234").baseOrder(order).build()
        );

        order.setOrderLines(orderLines);
        return order;
    }

}
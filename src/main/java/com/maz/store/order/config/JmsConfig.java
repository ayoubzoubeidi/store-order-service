package com.maz.store.order.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;


@Configuration
public class JmsConfig {

    public final static String VALIDATE_ORDER_QUEUE = "validate-order";
    public final static String VALIDATION_RESPONSE_QUEUE = "order-validation-response";
    public final static String ALLOCATE_ORDER_QUEUE = "allocate-order";
    public final static String ALLOCATION_RESPONSE_QUEUE = "order-allocation-response";
    public final static String DE_ALLOCATION_QUEUE = "order-de-allocation-request";
    public final static String CANCEL_QUEUE = "cancel-order";

    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();

        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        converter.setObjectMapper(objectMapper);

        return converter;
    }

}

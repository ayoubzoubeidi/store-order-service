package com.maz.store.order.web.controllers;

import com.maz.store.order.web.exceptions.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class OrderControllerExceptionHandler {


    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity orderNotFoundException(OrderNotFoundException exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", exception.getMessage());
        return new ResponseEntity(body, HttpStatus.NOT_FOUND);
    }

}

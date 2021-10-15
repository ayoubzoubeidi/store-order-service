package com.maz.store.order.web.controllers;

import com.maz.store.model.order.OrderDto;
import com.maz.store.order.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    public ResponseEntity order(@RequestBody OrderDto orderDto) {

        UUID orderId = orderService.createOrder(orderDto);

        return new ResponseEntity(orderId, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity checkOrder(@PathVariable UUID orderId) {

        OrderDto order = orderService.checkOrderStatus(orderId);

        return new ResponseEntity(order, HttpStatus.OK);
    }

    @PostMapping(value = "/{orderId}/cancel")
    public ResponseEntity cancelOrder(@PathVariable("orderId") UUID orderId) {
        return new ResponseEntity(orderService.cancelOrder(orderId), HttpStatus.OK);
    }

}

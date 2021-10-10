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
@RequestMapping("/order")
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


}

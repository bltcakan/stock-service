package com.stock.service.controller;

import com.stock.service.model.request.OrderRequest;
import com.stock.service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody OrderRequest orderRequest) throws Exception {
        orderService.createOrder(orderRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId)  {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }

}

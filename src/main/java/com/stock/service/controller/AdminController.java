package com.stock.service.controller;

import com.stock.service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final OrderService orderService;

    @PutMapping("/{orderId}/match")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.matchPendingOrder(orderId);
        return ResponseEntity.ok().build();
    }
}

package com.stock.service.controller;

import com.stock.service.model.dto.OrderDto;
import com.stock.service.model.request.OrderQueryRequest;
import com.stock.service.service.OrderLoadingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/order")
@RequiredArgsConstructor
public class OrderLoadingController {

    private final OrderLoadingService orderLoadingService;

    @PostMapping("/search")
    public ResponseEntity<Page<OrderDto>> search(@RequestBody OrderQueryRequest orderQueryRequest, Pageable pageable)  {
        Page<OrderDto> response = orderLoadingService.searchOrders(orderQueryRequest, pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

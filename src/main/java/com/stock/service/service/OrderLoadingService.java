package com.stock.service.service;

import com.stock.service.model.dto.OrderDto;
import com.stock.service.model.request.OrderQueryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderLoadingService {

    Page<OrderDto> searchOrders(OrderQueryRequest orderQueryRequest, Pageable pageable);
}

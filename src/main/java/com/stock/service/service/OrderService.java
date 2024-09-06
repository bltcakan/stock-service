package com.stock.service.service;

import com.stock.service.model.request.OrderRequest;

public interface OrderService {

    void createOrder(OrderRequest orderRequest) throws Exception;

    void cancelOrder(Long orderId, Long customerId);

    void matchPendingOrder(Long orderId);
}

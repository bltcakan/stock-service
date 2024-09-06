package com.stock.service.service;

import com.stock.service.model.Order;

public interface MatchOrderStrategy {

    void matchPendingOrder(Order order);
}

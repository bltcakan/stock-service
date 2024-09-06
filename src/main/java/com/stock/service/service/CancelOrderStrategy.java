package com.stock.service.service;

import com.stock.service.enumaration.AssetType;
import com.stock.service.enumaration.Side;
import com.stock.service.model.Order;

import java.math.BigDecimal;

public interface CancelOrderStrategy {

    void handleCancelOrder(Order order);
}

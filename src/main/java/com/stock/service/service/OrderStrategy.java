package com.stock.service.service;

import com.stock.service.enumaration.AssetType;

import java.math.BigDecimal;

public interface OrderStrategy {
    void handleOrder(Long customerId, AssetType assetType, BigDecimal size, BigDecimal price) throws Exception;
}

package com.stock.service.service.impl;

import com.stock.service.enumaration.AssetType;
import com.stock.service.model.Asset;
import com.stock.service.model.Order;
import com.stock.service.service.AssetService;
import com.stock.service.service.CancelOrderStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CancelBuyOrderStrategy implements CancelOrderStrategy {

    @Override
    public void handleCancelOrder(Order order) {
        Asset asset = order.getAsset();
        BigDecimal refundAmount = order.getPrice().multiply(order.getSize());
        asset.setUsableSize(asset.getUsableSize().add(refundAmount));
    }
}

package com.stock.service.service.impl;

import com.stock.service.model.Asset;
import com.stock.service.model.Order;
import com.stock.service.service.CancelOrderStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CancelSellOrderStrategy implements CancelOrderStrategy {

    @Override
    public void handleCancelOrder(Order order) {
        Asset asset = order.getAsset();
        asset.setUsableSize(asset.getUsableSize().add(order.getSize()));
    }
}

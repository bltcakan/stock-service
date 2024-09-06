package com.stock.service.service.impl;

import com.stock.service.exception.InsufficientBalanceException;
import com.stock.service.model.Asset;
import com.stock.service.model.Order;
import com.stock.service.service.AssetService;
import com.stock.service.service.MatchOrderStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchSellOrderStrategy implements MatchOrderStrategy {

    private final AssetService assetService;

    @Override
    public void matchPendingOrder(Order order) {
        Asset soldAsset = order.getAsset();
        if (soldAsset.getUsableSize().compareTo(order.getSize()) < 0) {
            throw new InsufficientBalanceException("Insufficient asset balance for SELL order.");
        }

        soldAsset.setSize(soldAsset.getSize().subtract(order.getSize()));
        soldAsset.setUsableSize(soldAsset.getUsableSize().subtract(order.getSize()));
        assetService.updateAsset(soldAsset);
    }
}

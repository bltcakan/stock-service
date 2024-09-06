package com.stock.service.service.impl;

import com.stock.service.exception.InsufficientBalanceException;
import com.stock.service.model.Asset;
import com.stock.service.model.Order;
import com.stock.service.service.AssetService;
import com.stock.service.service.MatchOrderStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class MatchBuyOrderStrategy implements MatchOrderStrategy {

    private final AssetService assetService;

    @Override
    public void matchPendingOrder(Order order) {
        BigDecimal orderSize = order.getSize();
        BigDecimal orderPrice = order.getPrice();
        Asset asset = order.getAsset();

        BigDecimal totalAmount = orderPrice.multiply(orderSize);
        if (asset.getUsableSize().compareTo(totalAmount) < 0) {
            throw new InsufficientBalanceException("Insufficient TRY balance for this order.");
        }

        asset.setSize(orderSize.subtract(totalAmount));
        asset.setUsableSize(asset.getUsableSize().subtract(totalAmount));
        assetService.updateAsset(asset);
    }
}

package com.stock.service.service.impl;

import com.stock.service.enumaration.AssetType;
import com.stock.service.exception.InsufficientBalanceException;
import com.stock.service.model.Asset;
import com.stock.service.service.AssetService;
import com.stock.service.service.OrderStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BuyOrderStrategy implements OrderStrategy {

    private final AssetService assetService;

    @Override
    public void handleOrder(Long customerId, AssetType assetType, BigDecimal size, BigDecimal price) throws Exception {
        Asset asset = assetService.getAssetByCustomerIdAndAssetType(customerId, assetType);

        BigDecimal requiredAmount = price.multiply(size);

        if (asset.getUsableSize().compareTo(requiredAmount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance to complete the order");
        }

        asset.setUsableSize(asset.getUsableSize().subtract(requiredAmount));

        assetService.updateAsset(asset);
    }
}

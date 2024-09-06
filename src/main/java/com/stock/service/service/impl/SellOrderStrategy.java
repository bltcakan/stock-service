package com.stock.service.service.impl;

import com.stock.service.enumaration.AssetType;
import com.stock.service.exception.InsufficientBalanceException;
import com.stock.service.model.Asset;
import com.stock.service.service.AssetService;
import com.stock.service.service.OrderStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellOrderStrategy implements OrderStrategy {

    private final AssetService assetService;

    @Override
    public void handleOrder(Long customerId, AssetType assetType, BigDecimal size, BigDecimal price) throws Exception {
        Asset asset = assetService.getAssetByCustomerIdAndAssetType(customerId, assetType);

        if (asset.getUsableSize().compareTo(size) < 0) {
            throw new InsufficientBalanceException("Insufficient " + assetType + " balance to complete the SELL order");
        }
        asset.setUsableSize(asset.getUsableSize().add(size));

        assetService.updateAsset(asset);
    }
}

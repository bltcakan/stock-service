package com.stock.service.service;

import com.stock.service.enumaration.AssetType;
import com.stock.service.model.Asset;
import com.stock.service.model.request.DepositMoneyRequest;
import com.stock.service.model.request.WithDrawMoneyRequest;

import java.math.BigDecimal;

public interface AssetService {
    Asset getAssetByCustomerIdAndAssetType(Long customerId, AssetType assetType);

    void updateAsset(Asset asset);

    void depositMoney(DepositMoneyRequest depositMoneyRequest);

    void withdrawMoney(WithDrawMoneyRequest withDrawMoneyRequest);
}

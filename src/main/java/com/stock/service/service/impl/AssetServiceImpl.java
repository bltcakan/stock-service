package com.stock.service.service.impl;

import com.stock.service.enumaration.AssetType;
import com.stock.service.exception.AssetNotFoundException;
import com.stock.service.model.Asset;
import com.stock.service.model.dto.UserDetailsDto;
import com.stock.service.model.request.DepositMoneyRequest;
import com.stock.service.model.request.WithDrawMoneyRequest;
import com.stock.service.repository.AssetRepository;
import com.stock.service.service.AssetService;
import com.stock.service.service.CustomUserDetailsService;
import com.stock.service.verification.AssetValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final AssetValidationService assetValidationService;

    @Override
    public Asset getAssetByCustomerIdAndAssetType(Long customerId, AssetType assetType) {
        return assetRepository.findByCustomerIdAndAssetType(customerId, assetType).orElseThrow(() ->
                new AssetNotFoundException(String.format("Asset %s not found for customer", assetType)));
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void updateAsset(Asset asset) {
        assetRepository.save(asset);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void depositMoney(DepositMoneyRequest depositMoneyRequest) {
        log.info("Deposit money request: {}", depositMoneyRequest);
        assetValidationService.validateAssetType(depositMoneyRequest.getAssetType());
        UserDetailsDto currentUserDetails = customUserDetailsService.getCurrentUserDetails();
        Asset asset = getAssetByCustomerIdAndAssetType(currentUserDetails.getId(), depositMoneyRequest.getAssetType());
        asset.setSize(asset.getSize().add(depositMoneyRequest.getAmount()));
        asset.setUsableSize(asset.getUsableSize().add(depositMoneyRequest.getAmount()));
        assetRepository.save(asset);
        log.info("Deposit money request: {}", depositMoneyRequest);
    }

    @Override
    public void withdrawMoney(WithDrawMoneyRequest withDrawMoneyRequest) {
        log.info("Withdraw money request: {}", withDrawMoneyRequest);
        UserDetailsDto currentUserDetails = customUserDetailsService.getCurrentUserDetails();
        assetValidationService.validateAssetType(withDrawMoneyRequest.getAssetType());
        assetValidationService.validateIban(withDrawMoneyRequest.getIban());
        assetValidationService.validateBalance(currentUserDetails.getId(), withDrawMoneyRequest.getAmount(), withDrawMoneyRequest.getAmount(), withDrawMoneyRequest.getAssetType());
        Asset asset = getAssetByCustomerIdAndAssetType(currentUserDetails.getId(), withDrawMoneyRequest.getAssetType());
        asset.setSize(asset.getSize().subtract(withDrawMoneyRequest.getAmount()));
        asset.setUsableSize(asset.getUsableSize().subtract(withDrawMoneyRequest.getAmount()));
        assetRepository.save(asset);
        log.info("Withdraw money sent to: {}", withDrawMoneyRequest.getIban());
    }


}

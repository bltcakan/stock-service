package com.stock.service.verification;

import com.stock.service.enumaration.AssetType;
import com.stock.service.exception.AssetNotFoundException;
import com.stock.service.exception.InsufficientBalanceException;
import com.stock.service.exception.InvalidAssetException;
import com.stock.service.exception.InvalidIbanException;
import com.stock.service.model.Asset;
import com.stock.service.repository.AssetRepository;
import com.stock.service.validator.IbanValidator;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.util.StringUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AssetValidationService {

    private final AssetRepository assetRepository;

    public void validateIban(String iban) {
        if (StringUtils.isBlank(iban)) {
            throw new InvalidIbanException("IBAN is required");
        }
        boolean validateIban = IbanValidator.validateIban(iban);
        if (!validateIban) {
            throw new InvalidIbanException("Invalid IBAN");
        }
    }

    public void validateAssetType(AssetType assetType) {
        if (!AssetType.TRY.equals(assetType)) {
            throw new InvalidAssetException("Only TRY asset allowed for trading");
        }
    }

    public void validateBalance(Long customerId, BigDecimal price, BigDecimal size, AssetType assetType)  {
        Asset tryAsset = assetRepository.findByCustomerIdAndAssetType(customerId, assetType)
                .orElseThrow(() -> new InsufficientBalanceException(String.format("Asset %s not found for customer", assetType)));

        if (tryAsset.getUsableSize().compareTo(price.multiply(size)) < 0) {
            throw new InsufficientBalanceException(String.format("Insufficient %s balance to place this order", assetType));
        }
    }

    public void validateSellOrder(Long customerId, AssetType assetType, BigDecimal size) {
        Asset asset = assetRepository.findByCustomerIdAndAssetType(customerId, assetType)
                .orElseThrow(() -> new AssetNotFoundException(assetType + " asset not found for customer"));

        if (asset.getUsableSize().compareTo(size) < 0) {
            throw new InsufficientBalanceException("Insufficient " + assetType + " balance to place this SELL order");
        }
    }

}

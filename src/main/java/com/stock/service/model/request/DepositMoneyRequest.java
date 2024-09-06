package com.stock.service.model.request;

import com.stock.service.enumaration.AssetType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class DepositMoneyRequest implements Serializable {
    private Long customerId;
    private BigDecimal amount;
    private AssetType assetType;
    private String username;

}

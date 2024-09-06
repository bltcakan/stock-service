package com.stock.service.model.request;

import com.stock.service.enumaration.AssetType;
import com.stock.service.enumaration.Side;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class OrderRequest implements Serializable {
    private Long customerId;
    private AssetType assetType;
    private Side side;
    private BigDecimal size;
    private BigDecimal price;
    private String username;

}

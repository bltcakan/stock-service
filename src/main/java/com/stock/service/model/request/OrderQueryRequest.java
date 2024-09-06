package com.stock.service.model.request;

import com.stock.service.enumaration.AssetType;
import com.stock.service.enumaration.Side;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderQueryRequest implements Serializable {
    private Long customerId;
    private String username;
    private AssetType assetType;
    private Side side;
    private LocalDateTime createdDate;
}

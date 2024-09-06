package com.stock.service.model.dto;

import com.stock.service.enumaration.AssetType;
import com.stock.service.enumaration.OrderStatus;
import com.stock.service.enumaration.Side;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderDto implements Serializable {
    private CustomerDto customerDto;
    private AssetType assetType;
    private Side side;
    private BigDecimal size;
    private BigDecimal price;
    private OrderStatus status;
    private LocalDateTime createdDate;
}

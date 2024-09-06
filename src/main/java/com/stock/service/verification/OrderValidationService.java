package com.stock.service.verification;


import com.stock.service.enumaration.AssetType;
import com.stock.service.enumaration.OrderStatus;
import com.stock.service.enumaration.Side;
import com.stock.service.exception.InvalidAssetException;
import com.stock.service.exception.OrderNotFoundException;
import com.stock.service.model.Order;
import com.stock.service.model.request.OrderRequest;
import com.stock.service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderValidationService {

    private final OrderRepository tradeOrderRepository;
    private final AssetValidationService assetValidationService;

    public void validate(OrderRequest orderRequest) throws Exception {
        if (Objects.isNull(orderRequest.getSide())) {
            throw new Exception("Side is required");
        }

        if(!AssetType.TRY.equals(orderRequest.getAssetType())) {
            throw new InvalidAssetException("Only TRY asset allowed for trading");
        }

        if (Side.BUY.equals(orderRequest.getSide())) {
            validateBuyOrder(orderRequest.getCustomerId(), orderRequest.getPrice(), orderRequest.getSize(), orderRequest.getAssetType());
        }
        if (Side.SELL.equals(orderRequest.getSide())) {
            validateSellOrder(orderRequest.getCustomerId(), orderRequest.getAssetType(), orderRequest.getSize());
        }

    }

    public void validateSellOrder(Long customerId, AssetType assetType, BigDecimal size) throws Exception {
        assetValidationService.validateSellOrder(customerId, assetType, size);
    }

    public void validateBuyOrder(Long customerId, BigDecimal price, BigDecimal size, AssetType assetType) throws Exception {
        assetValidationService.validateBalance(customerId, price, size, assetType);
    }

    public void validateMatchOrder(Long orderId) {
        Order order = tradeOrderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new OrderNotFoundException("Only PENDING orders can be matched");
        }
    }

    public void validateCancelOrder(Long orderId) {
        Order order = tradeOrderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new OrderNotFoundException("Only PENDING orders can be canceled");
        }
    }

}

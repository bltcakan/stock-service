package com.stock.service.service.impl;

import com.stock.service.enumaration.OrderStatus;
import com.stock.service.enumaration.Side;
import com.stock.service.exception.OrderNotFoundException;
import com.stock.service.model.Asset;
import com.stock.service.model.Customer;
import com.stock.service.model.Order;
import com.stock.service.model.dto.UserDetailsDto;
import com.stock.service.model.request.OrderRequest;
import com.stock.service.repository.OrderRepository;
import com.stock.service.service.*;
import com.stock.service.verification.AssetValidationService;
import com.stock.service.verification.OrderValidationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderValidationService orderValidationService;
    private final CustomerService customerService;
    private final AssetService assetService;
    private final OrderStrategyContext orderStrategyContext;
    private final CancelOrderStrategyContext cancelOrderStrategyContext;
    private final MatchOrderStrategyContext matchOrderStrategyContext;
    private final CustomUserDetailsService customUserDetailsService;


    @Override
    @Transactional(rollbackOn = Exception.class)
    public void createOrder(OrderRequest orderRequest) throws Exception {
        log.info("Order request received: {}", orderRequest);
        orderValidationService.validate(orderRequest);
        UserDetailsDto currentUserDetails = customUserDetailsService.getCurrentUserDetails();

        Side side = orderRequest.getSide();
        Long customerId = orderRequest.getCustomerId();
        BigDecimal size = orderRequest.getSize();
        BigDecimal price = orderRequest.getPrice();

        OrderStrategy orderStrategy = orderStrategyContext.getStrategy(side);
        orderStrategy.handleOrder(customerId, orderRequest.getAssetType(), size, price);

        Customer customer = customerService.getCustomerById(currentUserDetails.getId());
        Asset asset = assetService.getAssetByCustomerIdAndAssetType(orderRequest.getCustomerId(), orderRequest.getAssetType());

        Order order = new Order();
        order.setCustomer(customer);
        order.setAsset(asset);
        order.setSide(orderRequest.getSide());
        order.setSize(orderRequest.getSize());
        order.setPrice(orderRequest.getPrice());
        order.setStatus(OrderStatus.PENDING);

        orderRepository.save(order);
        log.info("Order created: {}", order);

    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void cancelOrder(Long orderId, Long customerId) {
        log.info("Order cancel request received. OrderId: {}, CustomerId: {}", orderId, customerId);
        UserDetailsDto currentUserDetails = customUserDetailsService.getCurrentUserDetails();
        orderValidationService.validateCancelOrder(orderId);
        Order order = orderRepository.findByIdAndCustomerIdAndStatus(orderId, currentUserDetails.getId(), OrderStatus.PENDING)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        cancelOrderStrategyContext.getStrategy(order.getSide()).handleCancelOrder(order);
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);

        log.info("Order canceled: {}", order);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void matchPendingOrder(Long orderId) {
        log.info("Order match request received. OrderId: {}", orderId);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        orderValidationService.validateMatchOrder(orderId);
        matchOrderStrategyContext.getStrategy(order.getSide()).matchPendingOrder(order);
        order.setStatus(OrderStatus.MATCHED);
        orderRepository.save(order);

        log.info("Order matched: {}", order);
    }
}

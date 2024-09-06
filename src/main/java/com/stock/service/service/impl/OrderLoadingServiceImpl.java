package com.stock.service.service.impl;

import com.stock.service.converter.OrderEntityToDtoConverter;
import com.stock.service.model.Order;
import com.stock.service.model.dto.OrderDto;
import com.stock.service.model.dto.UserDetailsDto;
import com.stock.service.model.request.OrderQueryRequest;
import com.stock.service.repository.OrderRepository;
import com.stock.service.service.CustomUserDetailsService;
import com.stock.service.service.OrderLoadingService;
import com.stock.service.specification.OrderSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLoadingServiceImpl implements OrderLoadingService {
    private final OrderRepository orderRepository;
    private final OrderEntityToDtoConverter orderEntityToDtoConverter;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public Page<OrderDto> searchOrders(OrderQueryRequest orderQueryRequest, Pageable pageable) {
        UserDetailsDto currentUserDetails = customUserDetailsService.getCurrentUserDetails();
        orderQueryRequest.setCustomerId(currentUserDetails.getId());
        orderQueryRequest.setUsername(currentUserDetails.getUsername());
        Specification<Order> orderSpecification = OrderSpecification.init(orderQueryRequest);
        Page<Order> orderPage = orderRepository.findAll(orderSpecification, pageable);
        if (orderPage.isEmpty()) {
            return Page.empty(pageable);
        }

        List<OrderDto> orderDtoList = orderPage.getContent().stream()
                .map(orderEntityToDtoConverter::convert)
                .collect(Collectors.toList());

        return new PageImpl<>(orderDtoList, pageable, orderPage.getTotalElements());

    }
}

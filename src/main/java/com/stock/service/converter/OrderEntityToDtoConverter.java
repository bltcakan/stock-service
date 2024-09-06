package com.stock.service.converter;

import com.stock.service.model.Order;
import com.stock.service.model.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEntityToDtoConverter {

    private final CustomerEntityToDtoConverter customerEntityToDtoConverter;

    public  OrderDto convert(Order orderEntity) {
        OrderDto orderDto = new OrderDto();
        orderDto.setCustomerDto(customerEntityToDtoConverter.convert(orderEntity.getCustomer()));
        orderDto.setAssetType(orderEntity.getAsset().getAssetType());
        orderDto.setSide(orderEntity.getSide());
        orderDto.setSize(orderEntity.getSize());
        orderDto.setPrice(orderEntity.getPrice());
        orderDto.setCreatedDate(orderEntity.getCreatedDate());
        orderDto.setStatus(orderEntity.getStatus());
        return orderDto;
    }
}

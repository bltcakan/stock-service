package com.stock.service.converter;

import com.stock.service.model.Customer;
import com.stock.service.model.dto.CustomerDto;
import org.springframework.stereotype.Component;

@Component
public class CustomerEntityToDtoConverter {
    public CustomerDto convert(Customer customerEntity) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customerEntity.getId());
        customerDto.setUsername(customerEntity.getUsername());
        return customerDto;
    }
}

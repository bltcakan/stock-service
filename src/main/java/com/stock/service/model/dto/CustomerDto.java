package com.stock.service.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CustomerDto implements Serializable {
    private Long id;
    private String username;
    private String address;
    private String phoneNumber;

}

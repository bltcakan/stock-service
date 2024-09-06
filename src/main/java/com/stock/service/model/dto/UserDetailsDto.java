package com.stock.service.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsDto {
    private Long id;
    private String username;
    private String role;
}

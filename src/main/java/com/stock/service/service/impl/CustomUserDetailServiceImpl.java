package com.stock.service.service.impl;

import com.stock.service.exception.UserNotFoundException;
import com.stock.service.model.dto.UserDetailsDto;
import com.stock.service.repository.CustomerRepository;
import com.stock.service.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailServiceImpl implements CustomUserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetailsDto getCurrentUserDetails() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        customerRepository.findByUsername(username)
                .map(customer -> {
                    UserDetailsDto userDetailsDto = new UserDetailsDto();
                    userDetailsDto.setId(customer.getId());
                    userDetailsDto.setUsername(customer.getUsername());
                    userDetailsDto.setRole(customer.getRole());
                    return userDetailsDto;
                }).orElseThrow(() -> new UserNotFoundException("User not found"));

        return null;
    }
}

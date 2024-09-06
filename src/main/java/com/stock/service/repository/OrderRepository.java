package com.stock.service.repository;

import com.stock.service.enumaration.OrderStatus;
import com.stock.service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    Optional<Order>findByIdAndCustomerIdAndStatus(Long orderId, Long customerId, OrderStatus status);

}

package com.stock.service.model;

import com.stock.service.enumaration.OrderStatus;
import com.stock.service.enumaration.Side;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name = "trade_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;

    @Enumerated(EnumType.STRING)
    private Side side;

    private BigDecimal size;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Version
    private Long version;

    @CreatedDate
    private LocalDateTime createdDate;
}

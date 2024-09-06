package com.stock.service.service;

import com.stock.service.enumaration.Side;
import com.stock.service.service.impl.BuyOrderStrategy;
import com.stock.service.service.impl.SellOrderStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class OrderStrategyContext {

    private final Map<Side, OrderStrategy> strategies = new HashMap<>();

    @Autowired
    public OrderStrategyContext(BuyOrderStrategy buyOrderStrategy, SellOrderStrategy sellOrderStrategy) {
        strategies.put(Side.BUY, buyOrderStrategy);
        strategies.put(Side.SELL, sellOrderStrategy);
    }

    public OrderStrategy getStrategy(Side side) {
        return strategies.get(side);
    }
}

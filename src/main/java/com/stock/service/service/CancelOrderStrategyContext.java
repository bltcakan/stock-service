package com.stock.service.service;

import com.stock.service.enumaration.Side;
import com.stock.service.service.impl.CancelBuyOrderStrategy;
import com.stock.service.service.impl.CancelSellOrderStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CancelOrderStrategyContext {

    private final Map<Side, CancelOrderStrategy> strategies = new HashMap<>();

    @Autowired
    public CancelOrderStrategyContext(CancelBuyOrderStrategy cancelBuyOrderStrategy, CancelSellOrderStrategy cancelSellOrderStrategy) {
        strategies.put(Side.BUY, cancelBuyOrderStrategy);
        strategies.put(Side.SELL, cancelSellOrderStrategy);
    }

    public CancelOrderStrategy getStrategy(Side side) {
        return strategies.get(side);
    }
}

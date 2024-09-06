package com.stock.service.service;

import com.stock.service.enumaration.Side;
import com.stock.service.service.impl.MatchBuyOrderStrategy;
import com.stock.service.service.impl.MatchSellOrderStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class MatchOrderStrategyContext {
    private final Map<Side, MatchOrderStrategy> strategies = new HashMap<>();

    @Autowired
    public MatchOrderStrategyContext(MatchBuyOrderStrategy buyOrderStrategy, MatchSellOrderStrategy sellOrderStrategy) {
        strategies.put(Side.BUY, buyOrderStrategy);
        strategies.put(Side.SELL, sellOrderStrategy);
    }

    public MatchOrderStrategy getStrategy(Side side) {
        return strategies.get(side);
    }
}

package com.stock.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = StockServiceApplication.class)
@ActiveProfiles("test")
public class StockServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}

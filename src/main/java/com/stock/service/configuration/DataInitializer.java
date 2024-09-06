package com.stock.service.configuration;

import com.stock.service.enumaration.AssetType;
import com.stock.service.enumaration.OrderStatus;
import com.stock.service.enumaration.Side;
import com.stock.service.model.Asset;
import com.stock.service.model.Customer;
import com.stock.service.model.Order;
import com.stock.service.repository.AssetRepository;
import com.stock.service.repository.CustomerRepository;
import com.stock.service.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(CustomerRepository customerRepository, OrderRepository orderRepository, PasswordEncoder passwordEncoder, AssetRepository assetRepository) {
        return args -> {
            Customer admin = new Customer();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            customerRepository.save(admin);

            Customer user1 = new Customer();
            user1.setUsername("user1");
            user1.setPassword(passwordEncoder.encode("user123"));
            user1.setRole("USER");
            customerRepository.save(user1);

            Customer user2 = new Customer();
            user2.setUsername("user2");
            user2.setPassword(passwordEncoder.encode("user123"));
            user2.setRole("USER");
            customerRepository.save(user2);


            Asset asset1 = new Asset();
            asset1.setCustomer(user1);
            asset1.setAssetType(AssetType.TRY);
            asset1.setSize(new BigDecimal("10000"));
            asset1.setUsableSize(new BigDecimal("10000"));


            Asset asset2 = new Asset();
            asset2.setCustomer(user2);
            asset2.setAssetType(AssetType.TRY);
            asset2.setSize(new BigDecimal("10000"));
            asset2.setUsableSize(new BigDecimal("10000"));

            assetRepository.save(asset1);
            assetRepository.save(asset2);


            Order order1 = new Order();
            order1.setCustomer(user1);
            order1.setAsset(asset1);
            order1.setSide(Side.BUY);
            order1.setSize(new BigDecimal("0.5"));
            order1.setPrice(new BigDecimal("45000"));
            order1.setStatus(OrderStatus.PENDING);
            orderRepository.save(order1);

            Order order2 = new Order();
            order2.setCustomer(user2);
            order2.setAsset(asset2);
            order2.setSide(Side.SELL);
            order2.setSize(new BigDecimal("1.5"));
            order2.setPrice(new BigDecimal("3000"));
            order2.setStatus(OrderStatus.MATCHED);
            orderRepository.save(order2);
        };
    }
}

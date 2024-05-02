package com.launchersoft.order_manager.config;

import com.launchersoft.order_manager.entity.Product;
import com.launchersoft.order_manager.repository.OrderRepository;
import com.launchersoft.order_manager.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(OrderRepository orderRepository, ProductRepository productRepository) {
        return args -> {
            log.info("Preloading " + productRepository.save(new Product("Ring", 1.000, "jewelry")));
            log.info("Preloading " + productRepository.save(new Product("Necklace", 2.000, "jewelry")));
            log.info("Preloading " + productRepository.save(new Product("Bracelet", 1.500, "jewelry")));
        };
    }

}

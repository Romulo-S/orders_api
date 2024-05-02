package com.launchersoft.order_manager.repository;

import com.launchersoft.order_manager.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {
}
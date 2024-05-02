package com.launchersoft.order_manager.service;

import com.launchersoft.order_manager.entity.Order;
import com.launchersoft.order_manager.entity.Product;
import com.launchersoft.order_manager.exception.OrderNotFoundException;
import com.launchersoft.order_manager.repository.OrderRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(OrderService.class);

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> all() {
        return orderRepository.findAll();
    }

    public Order newOrder(@RequestBody Order newOrder) {
        log.info("New order added: " + newOrder);
        return orderRepository.save(newOrder);
    }

    public Order one(@PathVariable Long id) {
        return
                orderRepository.findById(id)
                        .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Order replaceOrder(@RequestBody Order newOrder, @PathVariable Long id) {
        return
                orderRepository.findById(id)
                        .map(order -> {
                            order.setProducts(newOrder.getProducts());
                            order.setTotalCost(newOrder.getTotalCost());
                            return orderRepository.save(order);
                        })
                        .orElseGet(() -> {
                            newOrder.setOrder_id(id);
                            return orderRepository.save(newOrder);
                        });
    }

    public Order addProduct(@RequestBody Product newProduct, @PathVariable Long orderId) {
        return orderRepository.findById(orderId)
                .map(order -> {
                    order.getProducts().add(newProduct);
                    order.setTotalCost(order.getTotalCost() + newProduct.getPriceTag());
                    orderRepository.save(order);
                    log.info("Product added to order: {}", newProduct);
                    return order;
                })
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    public void deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
    }

    public void deleteProductFromOrder(@PathVariable Long id, @PathVariable Long productId) {
        orderRepository.findById(id)
                .map(order -> {
                    order.getProducts().removeIf(product -> product.getId().equals(productId));
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public void deleteAllOrders() {
        orderRepository.deleteAll();
    }

    public void deleteAllProductsFromOrder(@PathVariable Long id) {
        orderRepository.findById(id)
                .map(order -> {
                    order.getProducts().clear();
                    order.setTotalCost(0);
                    return orderRepository.save(order);
                })
                .orElseThrow(() -> new OrderNotFoundException(id));
    }


    public void calculateTotalCost(Order newOrder) {
        double totalCost = 0;
        for (Product product : newOrder.getProducts()) {
            totalCost += product.getPriceTag();
        }
        newOrder.setTotalCost(totalCost);
    }
}

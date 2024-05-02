package com.launchersoft.order_manager.controller;

import com.launchersoft.order_manager.entity.Order;
import com.launchersoft.order_manager.entity.Product;
import com.launchersoft.order_manager.service.OrderService;
import com.launchersoft.order_manager.service.ProductService;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private final OrderService orderService;
    private final ProductService productService;



    private static final org.slf4j.Logger log = LoggerFactory.getLogger(OrderController.class);


    OrderController(OrderService orderService, ProductService productService) {
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/orders")
    public List<Order> all() {
        return orderService.all();
    }

    @GetMapping("/order/{id}")
    public Order one(@PathVariable Long id) {
        return orderService.one(id);
    }

    @PostMapping("/order")
    public Order newOrder(@RequestBody Order newOrder) {
        return orderService.newOrder(newOrder);
    }

    @PutMapping("/order/{id}/addProduct/{productId}")
    public Order addProduct(@PathVariable Long id, @PathVariable Long productId) {
        Product newProduct = productService.findOne(productId);
        return orderService.addProduct(newProduct, id);
    }

    @GetMapping("/order/{id}/totalCost")
    public double totalCost(Long id) {
        return orderService.one(id).getTotalCost();
    }

    @GetMapping("/order/{id}/products")
    public List<Product> products(Long id) {
        return orderService.one(id).getProducts();
    }




}

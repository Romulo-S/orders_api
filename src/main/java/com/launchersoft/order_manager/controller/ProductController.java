package com.launchersoft.order_manager.controller;

import com.launchersoft.order_manager.entity.Product;
import com.launchersoft.order_manager.exception.ProductNotFoundException;
import com.launchersoft.order_manager.repository.ProductRepository;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/products")
    public List<Product> all() {
        return repository.findAll();
    }

    @PostMapping("/product")
    Product newProduct(@RequestBody Product newProduct) {
        log.info("New product added: " + newProduct); 
        return repository.save(newProduct);
    }

    @GetMapping("/product/{id}")
    Product one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PutMapping("/product/{id}")
    Product replaceProduct(@RequestBody Product newProduct, @PathVariable Long id) {
        return repository.findById(id)
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setPriceTag(newProduct.getPriceTag());
                    product.setCategory(newProduct.getCategory());
                    return repository.save(product);
                })
                .orElseGet(() -> {
                    newProduct.setId(id);
                    return repository.save(newProduct);
                });
    }

    @DeleteMapping("/product/{id}")
    void deleteProduct(@PathVariable Long id) {
        repository.deleteById(id);
    }
}

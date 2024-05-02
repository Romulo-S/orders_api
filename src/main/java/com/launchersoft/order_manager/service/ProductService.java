package com.launchersoft.order_manager.service;

import com.launchersoft.order_manager.entity.Product;
import com.launchersoft.order_manager.exception.ProductNotFoundException;
import com.launchersoft.order_manager.repository.ProductRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> all() {
        return repository.findAll();
    }

    public Product newProduct(Product newProduct) {
        log.info("New product added: " + newProduct);
        return repository.save(newProduct);
    }

    public Product findOne(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product replaceProduct(Product newProduct, Long id) {
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

    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }

    public List<Product> findByCategory(String category) {
        return repository.findByCategory(category);
    }

    public List<Product> findByName(String name) {
        return repository.findByName(name);
    }




}

package com.launchersoft.order_manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity(name = "products")
@NoArgsConstructor
@Getter
@Setter
public class Product {


    private @Id
    @GeneratedValue Long id;
    private String name;
    private double priceTag;
    private String category;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public Product(String name, double priceTag, String category) {
        this.name = name;
        this.priceTag = priceTag;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(priceTag, product.priceTag) && Objects.equals(category, product.category);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priceTag='" + priceTag + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, priceTag, category);
    }
}

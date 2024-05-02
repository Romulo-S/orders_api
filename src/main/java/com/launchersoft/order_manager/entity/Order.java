package com.launchersoft.order_manager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "orders")
@NoArgsConstructor
@Getter
@Setter
public class Order {

    private @Id
    @GeneratedValue Long order_id;

    @OneToMany(mappedBy = "order")
    private List<Product> products;

    private double totalCost;

}

package fr.dopolytech.cdp.order.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float price;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderProduct> products;

    public Order(float price, List<OrderProduct> products) {
        this.price = price;
        this.products = products;
    }

}

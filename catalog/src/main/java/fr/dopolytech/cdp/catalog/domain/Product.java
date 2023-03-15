package fr.dopolytech.cdp.catalog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("product")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    @Id
    private String id;

    private String name;

    private float price;

    public Product(String name, float price) {
        this.name = name;
        this.price = price;
    }
}

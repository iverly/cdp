package fr.dopolytech.cdp.catalog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("product")
@AllArgsConstructor
@Data
public class Product {
    @Id
    private String id;

    private String name;

    private int quantity;

    private float price;
}

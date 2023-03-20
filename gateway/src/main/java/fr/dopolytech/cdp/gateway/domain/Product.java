package fr.dopolytech.cdp.gateway.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class Product {

    private String id;

    private String name;

    private Float price;

    private Integer quantity;

}

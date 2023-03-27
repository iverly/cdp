package fr.dopolytech.cdp.gateway.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInventory {

    private Long id;

    private String productId;

    private Float price;

    private Integer quantity;

}

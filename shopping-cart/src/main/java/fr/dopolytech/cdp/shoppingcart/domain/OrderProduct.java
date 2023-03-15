package fr.dopolytech.cdp.shoppingcart.domain;

import lombok.Data;

@Data
public class OrderProduct {

    private String productId;

    private int quantity;

}

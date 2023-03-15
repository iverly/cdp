package fr.dopolytech.cdp.shoppingcart.domain;

import lombok.Data;

@Data
public class OrderProduct {

    private String productId;

    private String name;

    private float price;

    private int quantity;

}

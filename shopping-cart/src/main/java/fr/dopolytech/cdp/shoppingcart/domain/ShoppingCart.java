package fr.dopolytech.cdp.shoppingcart.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.LinkedList;

@AllArgsConstructor
@RedisHash("ShoppingCart")
@Data
public class ShoppingCart implements Serializable {

    @Id
    private String id;

    private LinkedList<OrderProduct> products;

}

package fr.dopolytech.cdp.shoppingcart.infrastructure.controllers.forms;

import fr.dopolytech.cdp.shoppingcart.domain.OrderProduct;
import fr.dopolytech.cdp.shoppingcart.domain.ShoppingCart;
import lombok.Data;

import java.util.LinkedList;

@Data
public class CreateShoppingCartForm {

    private final LinkedList<OrderProduct> products = new LinkedList<>();

    public ShoppingCart toShoppingCart() {
        return new ShoppingCart("1", this.products);
    }

}

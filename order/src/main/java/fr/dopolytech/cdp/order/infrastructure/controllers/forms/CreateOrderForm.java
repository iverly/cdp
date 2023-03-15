package fr.dopolytech.cdp.order.infrastructure.controllers.forms;

import fr.dopolytech.cdp.order.domain.Order;
import fr.dopolytech.cdp.order.domain.OrderProduct;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;

@Data
@NoArgsConstructor
public class CreateOrderForm {

    private float price;

    private LinkedList<OrderProduct> products = new LinkedList<>();

    public Order toOrder() {
        return new Order(this.price, this.products);
    }

}

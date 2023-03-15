package fr.dopolytech.cdp.order.infrastructure.controllers;

import fr.dopolytech.cdp.order.domain.Order;
import fr.dopolytech.cdp.order.infrastructure.controllers.forms.CreateOrderForm;
import fr.dopolytech.cdp.order.usecases.CreateOrder;
import fr.dopolytech.cdp.order.usecases.GetAllOrders;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
class OrderController {

    private final CreateOrder createOrder;

    private final GetAllOrders getAllOrders;

    @GetMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Order> getAllOrders(){
        return getAllOrders.getAll();
    }

    @PostMapping(value ="/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public Order createShoppingCart(@RequestBody CreateOrderForm createOrderForm) {
        return createOrder.create(createOrderForm.toOrder());
    }

}

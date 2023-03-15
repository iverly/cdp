package fr.dopolytech.cdp.order.usecases;

import fr.dopolytech.cdp.order.domain.Order;
import fr.dopolytech.cdp.order.domain.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateOrder {

    private final OrderRepository orderRepository;

    public Order create(Order order) {
        return this.orderRepository.save(order);
    }

}

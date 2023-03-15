package fr.dopolytech.cdp.order.usecases;

import fr.dopolytech.cdp.order.domain.Order;
import fr.dopolytech.cdp.order.domain.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllOrders {

    private final OrderRepository orderRepository;

    public List<Order> getAll() {
        return this.orderRepository.findAll();
    }

}

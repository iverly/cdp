package fr.dopolytech.cdp.order.usecases;

import fr.dopolytech.cdp.order.domain.Order;
import fr.dopolytech.cdp.order.domain.OrderRepository;
import fr.dopolytech.cdp.order.infrastructure.dtos.OrderCreatedDTO;
import fr.dopolytech.cdp.order.infrastructure.services.RabbitMQEventEmitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateOrder {

    private final OrderRepository orderRepository;

    private final RabbitMQEventEmitterService eventEmitterService;

    public Order create(Order order) {
        Order savedOrder = this.orderRepository.save(order);

        this.eventEmitterService.emit(
            "ORDER_CREATED",
            new OrderCreatedDTO(
                order.getId(),
                order.getPrice(),
                order.getProducts()
                    .stream()
                    .map(product -> new OrderCreatedDTO.OrderProduct(product.getProductId(), product.getQuantity()))
                    .toList()
            )
        );

        return savedOrder;
    }

}

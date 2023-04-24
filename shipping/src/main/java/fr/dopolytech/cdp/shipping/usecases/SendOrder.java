package fr.dopolytech.cdp.shipping.usecases;

import fr.dopolytech.cdp.shipping.domain.Shipping;
import fr.dopolytech.cdp.shipping.domain.ShippingRepository;
import fr.dopolytech.cdp.shipping.infrastructure.RabbitMQUriHelper;
import fr.dopolytech.cdp.shipping.infrastructure.dtos.SendOrderDTO;
import fr.dopolytech.cdp.shipping.infrastructure.services.RabbitMQEventEmitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendOrder {

    private final ShippingRepository shippingRepository;

    private final RabbitMQEventEmitterService eventEmitterService;

    public Shipping create(SendOrderDTO sendOrderDTO) {
        Shipping shipping = new Shipping(sendOrderDTO.getOrderId());

        Shipping savedPayment = shippingRepository.save(shipping);

        this.eventEmitterService.emit(
            RabbitMQUriHelper.Constants.RoutingKey.ORDER_SENT.getName(),
            sendOrderDTO
        );

        return savedPayment;
    }

}

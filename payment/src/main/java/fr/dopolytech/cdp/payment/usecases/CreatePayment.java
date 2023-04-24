package fr.dopolytech.cdp.payment.usecases;

import fr.dopolytech.cdp.payment.domain.Payment;
import fr.dopolytech.cdp.payment.domain.PaymentRepository;
import fr.dopolytech.cdp.payment.infrastructure.RabbitMQUriHelper;
import fr.dopolytech.cdp.payment.infrastructure.dtos.CreatePaymentDTO;
import fr.dopolytech.cdp.payment.infrastructure.services.RabbitMQEventEmitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreatePayment {

    private final PaymentRepository paymentRepository;

    private final RabbitMQEventEmitterService eventEmitterService;

    public Payment create(CreatePaymentDTO paymentDTO) {
        Payment payment = new Payment(
            paymentDTO.getPrice()
        );

        Payment savedPayment = this.paymentRepository.save(payment);
        paymentDTO.setPaymentId(savedPayment.getId());

        this.eventEmitterService.emit(
            RabbitMQUriHelper.Constants.RoutingKey.PAYMENT_CREATED.getName(),
            paymentDTO
        );

        return savedPayment;
    }

}

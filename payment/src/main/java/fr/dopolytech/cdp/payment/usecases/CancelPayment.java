package fr.dopolytech.cdp.payment.usecases;

import fr.dopolytech.cdp.payment.domain.Payment;
import fr.dopolytech.cdp.payment.domain.PaymentRepository;
import fr.dopolytech.cdp.payment.infrastructure.dtos.CancelPaymentDTO;
import fr.dopolytech.cdp.payment.infrastructure.dtos.CreatePaymentDTO;
import fr.dopolytech.cdp.payment.infrastructure.services.RabbitMQEventEmitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CancelPayment {

    private final PaymentRepository paymentRepository;

    public void cancel(CancelPaymentDTO paymentDTO) {
        Payment payment = this.paymentRepository.getById(paymentDTO.getPaymentId());
        this.paymentRepository.delete(payment);
    }

}

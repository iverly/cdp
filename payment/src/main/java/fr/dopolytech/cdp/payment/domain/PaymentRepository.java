package fr.dopolytech.cdp.payment.domain;

public interface PaymentRepository {

    Payment save(Payment payment);

    Payment getById(Long id);

    void delete(Payment payment);

}

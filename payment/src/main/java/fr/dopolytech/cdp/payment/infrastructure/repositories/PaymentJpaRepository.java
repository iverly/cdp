package fr.dopolytech.cdp.payment.infrastructure.repositories;

import fr.dopolytech.cdp.payment.domain.Payment;
import fr.dopolytech.cdp.payment.domain.PaymentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentJpaRepository extends JpaRepository<Payment, Long>, PaymentRepository {

}

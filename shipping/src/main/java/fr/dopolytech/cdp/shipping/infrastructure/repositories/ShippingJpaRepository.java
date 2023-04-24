package fr.dopolytech.cdp.shipping.infrastructure.repositories;

import fr.dopolytech.cdp.shipping.domain.Shipping;
import fr.dopolytech.cdp.shipping.domain.ShippingRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingJpaRepository extends JpaRepository<Shipping, Long>, ShippingRepository {

}

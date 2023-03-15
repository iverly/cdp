package fr.dopolytech.cdp.order.infrastructure.repositories;

import fr.dopolytech.cdp.order.domain.Order;
import fr.dopolytech.cdp.order.domain.OrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Long>, OrderRepository {

}

package fr.dopolytech.cdp.inventory.infrastructure.repositories;

import fr.dopolytech.cdp.inventory.domain.Product;
import fr.dopolytech.cdp.inventory.domain.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Long>, ProductRepository {

}

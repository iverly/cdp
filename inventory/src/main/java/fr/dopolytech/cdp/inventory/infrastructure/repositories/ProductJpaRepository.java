package fr.dopolytech.cdp.inventory.infrastructure.repositories;

import fr.dopolytech.cdp.inventory.domain.Product;
import fr.dopolytech.cdp.inventory.domain.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Long>, ProductRepository {

    @Query("SELECT p FROM products p WHERE p.productId = ?1")
    Product findByProductId(String productId);

}

package fr.dopolytech.cdp.shoppingcart.infrastructure.repositories;

import fr.dopolytech.cdp.shoppingcart.domain.ShoppingCart;
import fr.dopolytech.cdp.shoppingcart.domain.ShoppingCartRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRedisRepository extends CrudRepository<ShoppingCart, String>, ShoppingCartRepository {
}

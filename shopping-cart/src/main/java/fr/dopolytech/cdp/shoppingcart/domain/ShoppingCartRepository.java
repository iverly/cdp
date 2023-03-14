package fr.dopolytech.cdp.shoppingcart.domain;

import java.util.Optional;

public interface ShoppingCartRepository {

    Optional<ShoppingCart> findById(String userId);

    ShoppingCart save(ShoppingCart shoppingCart);

}

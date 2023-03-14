package fr.dopolytech.cdp.shoppingcart.usecases;

import fr.dopolytech.cdp.shoppingcart.domain.ShoppingCart;
import fr.dopolytech.cdp.shoppingcart.domain.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateShoppingCart {

    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCart create(ShoppingCart shoppingCart) {
        return this.shoppingCartRepository.save(shoppingCart);
    }

}

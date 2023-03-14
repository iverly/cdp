package fr.dopolytech.cdp.shoppingcart.usecases;

import fr.dopolytech.cdp.shoppingcart.domain.ShoppingCart;
import fr.dopolytech.cdp.shoppingcart.domain.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class GetShoppingCart {

    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCart get(String id) {
        try {
            return this.shoppingCartRepository.findById(id).orElseThrow();
        } catch (Exception e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND
            );
        }
    }

}

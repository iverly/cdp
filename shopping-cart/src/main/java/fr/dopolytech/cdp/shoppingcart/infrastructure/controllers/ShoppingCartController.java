package fr.dopolytech.cdp.shoppingcart.infrastructure.controllers;

import fr.dopolytech.cdp.shoppingcart.domain.ShoppingCart;
import fr.dopolytech.cdp.shoppingcart.infrastructure.controllers.forms.CreateShoppingCartForm;
import fr.dopolytech.cdp.shoppingcart.usecases.CreateShoppingCart;
import fr.dopolytech.cdp.shoppingcart.usecases.GetShoppingCart;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
class ShoppingCartController {

    private final CreateShoppingCart createShoppingCart;

    private final GetShoppingCart getShoppingCart;

    @GetMapping(value = "/shopping-carts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ShoppingCart getShoppingCart(){
        return getShoppingCart.get("1");
    }

    @PostMapping(value ="/shopping-carts", produces = MediaType.APPLICATION_JSON_VALUE)
    public ShoppingCart createShoppingCart(@RequestBody CreateShoppingCartForm createShoppingCartForm) {
        return createShoppingCart.create(createShoppingCartForm.toShoppingCart());
    }

}

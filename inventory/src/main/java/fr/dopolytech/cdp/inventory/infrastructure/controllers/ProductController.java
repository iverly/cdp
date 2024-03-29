package fr.dopolytech.cdp.inventory.infrastructure.controllers;

import fr.dopolytech.cdp.inventory.domain.Product;
import fr.dopolytech.cdp.inventory.usecases.GetAllProducts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
class ProductController {

    private final GetAllProducts getAllProducts;

    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getAllProducts(){
        return getAllProducts.getAll();
    }

}

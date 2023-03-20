package fr.dopolytech.cdp.gateway.infrastructure.controllers;

import fr.dopolytech.cdp.gateway.domain.Product;
import fr.dopolytech.cdp.gateway.infrastructure.services.ProductAggregatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {

    private final ProductAggregatorService productAggregatorService;

    @GetMapping("/products")
    public Mono<ResponseEntity<List<Product>>> getProduct() {
        return this.productAggregatorService.getProducts()
            .map(ResponseEntity::ok);
    }

}

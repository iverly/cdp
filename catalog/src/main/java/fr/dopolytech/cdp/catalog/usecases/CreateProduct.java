package fr.dopolytech.cdp.catalog.usecases;

import fr.dopolytech.cdp.catalog.domain.Product;
import fr.dopolytech.cdp.catalog.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateProduct {

    private final ProductRepository productRepository;

    public Product create(Product product) {
        return this.productRepository.insert(product);
    }

}

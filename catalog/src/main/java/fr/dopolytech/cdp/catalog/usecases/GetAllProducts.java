package fr.dopolytech.cdp.catalog.usecases;

import fr.dopolytech.cdp.catalog.domain.Product;
import fr.dopolytech.cdp.catalog.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllProducts {

    private final ProductRepository productRepository;

    public List<Product> getAll() {
        return this.productRepository.findAll();
    }

}

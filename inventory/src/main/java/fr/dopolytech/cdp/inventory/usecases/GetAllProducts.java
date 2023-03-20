package fr.dopolytech.cdp.inventory.usecases;

import fr.dopolytech.cdp.inventory.domain.Product;
import fr.dopolytech.cdp.inventory.domain.ProductRepository;
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

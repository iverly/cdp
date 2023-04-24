package fr.dopolytech.cdp.inventory.usecases;

import fr.dopolytech.cdp.inventory.domain.Product;
import fr.dopolytech.cdp.inventory.domain.ProductRepository;
import fr.dopolytech.cdp.inventory.infrastructure.dtos.IncreaseStockDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IncreaseStock {

    private final ProductRepository productRepository;

    public void increaseStock(IncreaseStockDTO increaseStockDTO) {
        increaseStockDTO.getProducts().forEach(po -> {
            Product product = this.productRepository.findByProductId(po.getProductId());

            if (product == null) {
                throw new RuntimeException("Product not found");
            }

            product.setQuantity(product.getQuantity() + po.getQuantity());
            this.productRepository.save(product);
        });

        // todo: add logs in database
    }

}

package fr.dopolytech.cdp.inventory.usecases;

import fr.dopolytech.cdp.inventory.domain.Product;
import fr.dopolytech.cdp.inventory.domain.ProductRepository;
import fr.dopolytech.cdp.inventory.infrastructure.dtos.DecreaseStockDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DecreaseStock {

    private final ProductRepository productRepository;

    public void decreaseStock(DecreaseStockDTO decreaseStockDTO) {
        decreaseStockDTO.getProducts().forEach(po -> {
            Product product = this.productRepository.findByProductId(po.getProductId());

            if (product == null) {
                throw new RuntimeException("Product not found");
            }

            if (product.getQuantity() < po.getQuantity()) {
                throw new RuntimeException("Not enough product");
            }

            product.setQuantity(product.getQuantity() - po.getQuantity());
            this.productRepository.save(product);
        });

        // todo: add logs in database
    }

}

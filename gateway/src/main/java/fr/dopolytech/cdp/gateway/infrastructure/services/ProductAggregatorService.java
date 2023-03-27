package fr.dopolytech.cdp.gateway.infrastructure.services;

import fr.dopolytech.cdp.gateway.domain.Product;
import fr.dopolytech.cdp.gateway.domain.ProductInformation;
import fr.dopolytech.cdp.gateway.domain.ProductInventory;
import fr.dopolytech.cdp.gateway.infrastructure.clients.ProductInformationClient;
import fr.dopolytech.cdp.gateway.infrastructure.clients.ProductInventoryClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductAggregatorService {

    private final ProductInformationClient productInformationClient;

    private final ProductInventoryClient productInventoryClient;

    public Mono<List<Product>> getProducts() {
        return Mono.zip(
            this.productInformationClient.getProducts(),
            this.productInventoryClient.getProducts()
        ).map(this::combine);
    }

    private List<Product> combine(Tuple2<List<ProductInformation>, List<ProductInventory>> tuple){
        List<ProductInformation> productInformationList = tuple.getT1();
        List<ProductInventory> productInventoryList = tuple.getT2();

        return productInformationList.stream()
            .map(productInformation -> {
                ProductInventory productInventory = productInventoryList.stream()
                    .filter(pi -> pi.getProductId().equals(productInformation.getId()))
                    .findFirst()
                    .orElse(new ProductInventory(0L, productInformation.getId(), -1f, -1));

                return Product.of(
                    productInformation.getId(),
                    productInformation.getName(),
                    productInventory.getPrice(),
                    productInventory.getQuantity()
                );
            })
            .toList();
    }

}

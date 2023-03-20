package fr.dopolytech.cdp.gateway.infrastructure.clients;

import fr.dopolytech.cdp.gateway.UriConfiguration;
import fr.dopolytech.cdp.gateway.domain.ProductInventory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
public class ProductInventoryClient {

    private final WebClient client;

    public ProductInventoryClient(WebClient.Builder builder, UriConfiguration uriConfiguration) {
        this.client = builder.baseUrl(uriConfiguration.getInventoryService()).build();
    }

    public Mono<List<ProductInventory>> getProducts(){
        return this.client
            .get()
            .uri("/products")
            .retrieve()
            .bodyToFlux(ProductInventory.class)
            .collectList()
            .onErrorReturn(Collections.emptyList());
    }

}

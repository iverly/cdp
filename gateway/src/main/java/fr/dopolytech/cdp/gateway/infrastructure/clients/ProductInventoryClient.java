package fr.dopolytech.cdp.gateway.infrastructure.clients;

import fr.dopolytech.cdp.gateway.UriConfiguration;
import fr.dopolytech.cdp.gateway.domain.ProductInventory;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductInventoryClient {

    private final UriConfiguration uriConfiguration;

    private WebClient buildWebClient() {
        return WebClient.builder()
                .baseUrl("http://" + this.uriConfiguration.getInventoryService())
                .build();
    }

    public Mono<List<ProductInventory>> getProducts(){
        return this.buildWebClient()
            .get()
            .uri("/products")
            .retrieve()
            .bodyToFlux(ProductInventory.class)
            .collectList()
            .onErrorReturn(Collections.emptyList());
    }

}

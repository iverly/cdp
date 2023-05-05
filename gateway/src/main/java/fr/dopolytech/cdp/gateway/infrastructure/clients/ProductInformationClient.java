package fr.dopolytech.cdp.gateway.infrastructure.clients;

import fr.dopolytech.cdp.gateway.UriConfiguration;
import fr.dopolytech.cdp.gateway.domain.ProductInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductInformationClient {

    private final UriConfiguration uriConfiguration;

    private WebClient buildWebClient() {
        return WebClient.builder()
                .baseUrl("http://" + this.uriConfiguration.getCatalogService())
                .build();
    }

    public Mono<List<ProductInformation>> getProducts() {
        return this.buildWebClient()
            .get()
            .uri("/products")
            .retrieve()
            .bodyToFlux(ProductInformation.class)
            .collectList()
            .onErrorReturn(Collections.emptyList());
    }

}

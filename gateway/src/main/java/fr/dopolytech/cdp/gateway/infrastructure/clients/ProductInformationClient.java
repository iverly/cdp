package fr.dopolytech.cdp.gateway.infrastructure.clients;

import fr.dopolytech.cdp.gateway.UriConfiguration;
import fr.dopolytech.cdp.gateway.domain.ProductInformation;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
public class ProductInformationClient {

    private final WebClient client;

    public ProductInformationClient(WebClient.Builder builder, UriConfiguration uriConfiguration) {
        this.client = builder.baseUrl(uriConfiguration.getCatalogService()).build();
    }

    public Mono<List<ProductInformation>> getProducts(){
        return this.client
            .get()
            .uri("/products")
            .retrieve()
            .bodyToFlux(ProductInformation.class)
            .collectList()
            .onErrorReturn(Collections.emptyList());
    }

}

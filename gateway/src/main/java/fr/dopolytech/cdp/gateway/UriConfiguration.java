package fr.dopolytech.cdp.gateway;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class UriConfiguration {

    @Getter
    private final String catalogService = "catalog-service";

    @Getter
    private final String shoppingCartService = "shopping-cart-service";

    @Getter
    private final String orderService = "order-service";

    @Getter
    private final String inventoryService = "inventory-service";

}

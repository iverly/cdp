package fr.dopolytech.cdp.gateway;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class UriConfiguration {

    @Getter
    private final String catalogService = "http://localhost:3001";

    @Getter
    private final String shoppingCartService = "http://localhost:3002";

    @Getter
    private final String orderService = "http://localhost:3003";

    @Getter
    private final String inventoryService = "http://localhost:3004";

}

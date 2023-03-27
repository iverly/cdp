package fr.dopolytech.cdp.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableConfigurationProperties(UriConfiguration.class)
@RestController
@EnableDiscoveryClient
public class GatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder, UriConfiguration uriConfiguration) {
		String catalogUri = uriConfiguration.getCatalogService();
		String shoppingCartUri = uriConfiguration.getShoppingCartService();
		String orderUri = uriConfiguration.getOrderService();

		return builder.routes()
			.route(p -> p
				.path("/products/**")
				.uri("lb://" + catalogUri))
			.route(p -> p
				.path("/shopping-carts/**")
				.uri("lb://" + shoppingCartUri))
			.route(p -> p
					.path("/orders/**")
					.uri("lb://" + orderUri))
			.build();
	}

}

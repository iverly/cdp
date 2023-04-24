package fr.dopolytech.cdp.inventory.infrastructure.routes;

import fr.dopolytech.cdp.inventory.infrastructure.RabbitMQUriHelper;
import fr.dopolytech.cdp.inventory.infrastructure.dtos.IncreaseStockDTO;
import fr.dopolytech.cdp.inventory.usecases.DecreaseStock;
import fr.dopolytech.cdp.inventory.usecases.IncreaseStock;
import fr.dopolytech.cdp.saga.infrastructure.dtos.DecreaseStockDTO;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import static fr.dopolytech.cdp.inventory.infrastructure.RabbitMQUriHelper.Constants.*;

@Component
@RequiredArgsConstructor
public class DecreaseStockRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // Decrease stock
        from(RabbitMQUriHelper.constructSagaUri(Exchange.INVENTORY, Queue.IN, RoutingKey.DECREASE_STOCK))
            .unmarshal().json(JsonLibrary.Jackson, DecreaseStockDTO.class)
            .doTry()
                .log("Decrease stock: ${body}")
                .bean(DecreaseStock.class, "decreaseStock(${body})")
                .marshal().json(JsonLibrary.Jackson)
                .to(RabbitMQUriHelper.constructSagaUri(Exchange.INVENTORY, Queue.OUT, RoutingKey.STOCK_DECREASED))
            .doCatch(Exception.class)
                .log("Error while decreasing stock: ${body}")
                .marshal().json(JsonLibrary.Jackson)
                .to(RabbitMQUriHelper.constructSagaUri(Exchange.INVENTORY, Queue.OUT, RoutingKey.DECREASE_STOCK_FAILED))
                .throwException(Exception.class, "Error while decreasing stock")
            .end();

        // Compensation
        from(RabbitMQUriHelper.constructSagaUri(Exchange.INVENTORY, Queue.IN, RoutingKey.REVERT_DECREASED_STOCK))
            .unmarshal().json(JsonLibrary.Jackson, IncreaseStockDTO.class)
            .log("Revert decrease stock: ${body}")
            .bean(IncreaseStock.class, "increaseStock(${body})");
    }

}

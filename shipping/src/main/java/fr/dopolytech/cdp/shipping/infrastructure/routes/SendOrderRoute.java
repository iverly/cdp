package fr.dopolytech.cdp.shipping.infrastructure.routes;

import fr.dopolytech.cdp.shipping.infrastructure.RabbitMQUriHelper;
import fr.dopolytech.cdp.shipping.infrastructure.dtos.SendOrderDTO;
import fr.dopolytech.cdp.shipping.usecases.SendOrder;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import static fr.dopolytech.cdp.shipping.infrastructure.RabbitMQUriHelper.Constants.*;

@Component
@RequiredArgsConstructor
public class SendOrderRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // Send order
        from(RabbitMQUriHelper.constructSagaUri(Exchange.SHIPPING, Queue.IN, RoutingKey.SEND_ORDER))
            .unmarshal().json(JsonLibrary.Jackson, SendOrderDTO.class)
            .doTry()
                .log("Send order: ${body}")
                .bean(SendOrder.class, "create(${body})")
//                .marshal().json(JsonLibrary.Jackson)
//                .to(RabbitMQUriHelper.constructSagaUri(Exchange.SHIPPING, Queue.OUT, RoutingKey.ORDER_SENT))
            .doCatch(Exception.class)
                .log("Error while shipping: ${body}")
                .marshal().json(JsonLibrary.Jackson)
                .to(RabbitMQUriHelper.constructSagaUri(Exchange.SHIPPING, Queue.OUT, RoutingKey.SEND_ORDER_FAILED))
                .throwException(Exception.class, "Error while paying")
            .end();
    }

}

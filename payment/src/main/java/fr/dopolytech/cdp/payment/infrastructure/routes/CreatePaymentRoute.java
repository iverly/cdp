package fr.dopolytech.cdp.payment.infrastructure.routes;

import fr.dopolytech.cdp.payment.infrastructure.RabbitMQUriHelper;
import fr.dopolytech.cdp.payment.infrastructure.dtos.CancelPaymentDTO;
import fr.dopolytech.cdp.payment.infrastructure.dtos.CreatePaymentDTO;
import fr.dopolytech.cdp.payment.usecases.CancelPayment;
import fr.dopolytech.cdp.payment.usecases.CreatePayment;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import static fr.dopolytech.cdp.payment.infrastructure.RabbitMQUriHelper.Constants.*;

@Component
@RequiredArgsConstructor
public class CreatePaymentRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // Create payment
        from(RabbitMQUriHelper.constructSagaUri(Exchange.PAYMENT, Queue.IN, RoutingKey.CREATE_PAYMENT))
            .unmarshal().json(JsonLibrary.Jackson, CreatePaymentDTO.class)
            .doTry()
                .log("Create payment: ${body}")
                .bean(CreatePayment.class, "create(${body})")
                .marshal().json(JsonLibrary.Jackson)
                .to(RabbitMQUriHelper.constructSagaUri(Exchange.PAYMENT, Queue.OUT, RoutingKey.PAYMENT_CREATED))
            .doCatch(Exception.class)
                .log("Error while paying: ${body}")
                .marshal().json(JsonLibrary.Jackson)
                .to(RabbitMQUriHelper.constructSagaUri(Exchange.PAYMENT, Queue.OUT, RoutingKey.PAYMENT_FAILED))
                .throwException(Exception.class, "Error while paying")
            .end();

        // Compensation
        from(RabbitMQUriHelper.constructSagaUri(Exchange.PAYMENT, Queue.IN, RoutingKey.CANCEL_PAYMENT))
            .unmarshal().json(JsonLibrary.Jackson, CancelPaymentDTO.class)
            .log("Cancel payment: ${body}")
            .bean(CancelPayment.class, "cancel(${body})");
    }

}

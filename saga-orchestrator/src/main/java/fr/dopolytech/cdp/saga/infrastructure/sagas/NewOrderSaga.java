package fr.dopolytech.cdp.saga.infrastructure.sagas;

import fr.dopolytech.cdp.saga.infrastructure.RabbitMQUriHelper;
import fr.dopolytech.cdp.saga.infrastructure.dtos.*;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import static fr.dopolytech.cdp.saga.infrastructure.RabbitMQUriHelper.Constants.*;

@Component
@RequiredArgsConstructor
public class NewOrderSaga extends RouteBuilder {

    @Override
    public void configure() {
        // ====================================================================================
        // New order created
        // ====================================================================================

        from(RabbitMQUriHelper.constructSagaUri(Exchange.ORDER, Queue.OUT, RoutingKey.ORDER_CREATED))
            .to("direct:newOrder/updateStock");

        // ====================================================================================
        // Update stock step
        // ====================================================================================

        from("direct:newOrder/updateStock")
            .unmarshal().json(JsonLibrary.Jackson, OrderCreatedDTO.class)
            .process(exchange -> {
                OrderCreatedDTO orderCreatedDTO = exchange.getIn().getBody(OrderCreatedDTO.class);

                // convert to stock update DTO
                DecreaseStockDTO decreaseStockDTO = new DecreaseStockDTO(
                    orderCreatedDTO.getOrderId(),
                    orderCreatedDTO.getPrice(),
                    orderCreatedDTO.getProducts()
                        .stream()
                        .map(product -> new DecreaseStockDTO.OrderProduct(
                            product.getProductId(),
                            product.getQuantity()
                        ))
                        .toList()
                );

                exchange.getIn().setBody(decreaseStockDTO, DecreaseStockDTO.class);
            })
            .marshal().json(JsonLibrary.Jackson)
            .to(RabbitMQUriHelper.constructSagaUri(Exchange.INVENTORY, Queue.IN, RoutingKey.DECREASE_STOCK));

        // Update stock success
        from(RabbitMQUriHelper.constructSagaUri(Exchange.INVENTORY, Queue.OUT, RoutingKey.STOCK_DECREASED))
            .to("direct:newOrder/createPayment");

        // Update stock failure
        from(RabbitMQUriHelper.constructSagaUri(Exchange.INVENTORY, Queue.OUT, RoutingKey.DECREASE_STOCK_FAILED))
            .unmarshal().json(JsonLibrary.Jackson, DecreaseStockDTO.class)
            .process(exchange -> {
                DecreaseStockDTO decreaseStockDTO = exchange.getIn().getBody(DecreaseStockDTO.class);

                // convert to cancel payment DTO
                CancelOrderDTO cancelOrderDTO = new CancelOrderDTO(
                    decreaseStockDTO.getOrderId()
                );

                exchange.getIn().setBody(cancelOrderDTO, CancelOrderDTO.class);
            })
            .marshal().json(JsonLibrary.Jackson)
            .to("direct:newOrder/cancelOrder");

        // ====================================================================================
        // Create payment step
        // ====================================================================================

        from("direct:newOrder/createPayment")
            .unmarshal().json(JsonLibrary.Jackson, DecreaseStockDTO.class)
            .process(exchange -> {
                DecreaseStockDTO decreaseStockDTO = exchange.getIn().getBody(DecreaseStockDTO.class);

                // convert to create payment DTO
                CreatePaymentDTO createPaymentDTO = new CreatePaymentDTO(
                    decreaseStockDTO.getOrderId(),
                    null,
                    decreaseStockDTO.getPrice(),
                    decreaseStockDTO.getProducts()
                        .stream()
                        .map(product -> new CreatePaymentDTO.OrderProduct(
                                product.getProductId(),
                                product.getQuantity()
                        ))
                        .toList()
                );

                exchange.getIn().setBody(createPaymentDTO, CreatePaymentDTO.class);
            })
            .marshal().json(JsonLibrary.Jackson)
            .to(RabbitMQUriHelper.constructSagaUri(Exchange.PAYMENT, Queue.IN, RoutingKey.CREATE_PAYMENT));

        // Create payment success
        from(RabbitMQUriHelper.constructSagaUri(Exchange.PAYMENT, Queue.OUT, RoutingKey.PAYMENT_CREATED))
            .to("direct:newOrder/sendOrder");

        // Update stock failure
        from(RabbitMQUriHelper.constructSagaUri(Exchange.PAYMENT, Queue.OUT, RoutingKey.PAYMENT_FAILED))
            .unmarshal().json(JsonLibrary.Jackson, CreatePaymentDTO.class)
            .process(exchange -> {
                CreatePaymentDTO createPaymentDTO = exchange.getIn().getBody(CreatePaymentDTO.class);

                // convert to cancel payment DTO
                IncreaseStockDTO increaseStockDTO = new IncreaseStockDTO(
                    createPaymentDTO.getOrderId(),
                    createPaymentDTO.getProducts()
                        .stream()
                        .map(product -> new IncreaseStockDTO.OrderProduct(
                                product.getProductId(),
                                product.getQuantity()
                        ))
                        .toList()
                );

                exchange.getIn().setBody(increaseStockDTO, IncreaseStockDTO.class);
            })
            .marshal().json(JsonLibrary.Jackson)
            .to("direct:newOrder/revertDecreaseStock")
            .process(exchange -> {
                IncreaseStockDTO increaseStockDTO = exchange.getIn().getBody(IncreaseStockDTO.class);

                // convert to cancel payment DTO
                CancelOrderDTO cancelOrderDTO = new CancelOrderDTO(
                    increaseStockDTO.getOrderId()
                );

                exchange.getIn().setBody(cancelOrderDTO, CancelOrderDTO.class);
            })
            .marshal().json(JsonLibrary.Jackson)
            .to("direct:newOrder/cancelOrder");

        // ====================================================================================
        // Send order step
        // ====================================================================================

        from("direct:newOrder/sendOrder")
            .unmarshal().json(JsonLibrary.Jackson, CreatePaymentDTO.class)
            .process(exchange -> {
                CreatePaymentDTO createPaymentDTO = exchange.getIn().getBody(CreatePaymentDTO.class);

                // convert to create payment DTO
                SendOrderDTO sendOrderDTO = new SendOrderDTO(
                    createPaymentDTO.getOrderId(),
                    createPaymentDTO.getPaymentId(),
                    createPaymentDTO.getPrice(),
                    createPaymentDTO.getProducts()
                        .stream()
                        .map(product -> new SendOrderDTO.OrderProduct(
                                product.getProductId(),
                                product.getQuantity()
                        ))
                        .toList()
                );

                exchange.getIn().setBody(sendOrderDTO, SendOrderDTO.class);
            })
            .marshal().json(JsonLibrary.Jackson)
            .to(RabbitMQUriHelper.constructSagaUri(Exchange.SHIPPING, Queue.IN, RoutingKey.SEND_ORDER));

        // Send order failure
        from(RabbitMQUriHelper.constructSagaUri(Exchange.SHIPPING, Queue.OUT, RoutingKey.SEND_ORDER_FAILED))
            .unmarshal().json(JsonLibrary.Jackson, SendOrderDTO.class)
            .process(exchange -> {
                SendOrderDTO sendOrderDTO = exchange.getIn().getBody(SendOrderDTO.class);

                // convert to cancel payment DTO
                CancelPaymentDTO cancelPaymentDTO = new CancelPaymentDTO(
                    sendOrderDTO.getOrderId(),
                    sendOrderDTO.getPaymentId(),
                    sendOrderDTO.getPrice(),
                    sendOrderDTO.getProducts()
                        .stream()
                        .map(product -> new CancelPaymentDTO.OrderProduct(
                                product.getProductId(),
                                product.getQuantity()
                        ))
                        .toList()
                );

                exchange.getIn().setBody(cancelPaymentDTO, CancelPaymentDTO.class);
            })
            .marshal().json(JsonLibrary.Jackson)
            .to("direct:newOrder/cancelPayment")
            .unmarshal().json(JsonLibrary.Jackson, SendOrderDTO.class)
            .process(exchange -> {
                CancelPaymentDTO cancelPaymentDTO = exchange.getIn().getBody(CancelPaymentDTO.class);

                // convert to cancel payment DTO
                IncreaseStockDTO increaseStockDTO = new IncreaseStockDTO(
                    cancelPaymentDTO.getOrderId(),
                    cancelPaymentDTO.getProducts()
                        .stream()
                        .map(product -> new IncreaseStockDTO.OrderProduct(
                                product.getProductId(),
                                product.getQuantity()
                        ))
                        .toList()
                );

                exchange.getIn().setBody(increaseStockDTO, IncreaseStockDTO.class);
            })
            .marshal().json(JsonLibrary.Jackson)
            .to("direct:newOrder/revertDecreaseStock")
            .unmarshal().json(JsonLibrary.Jackson, SendOrderDTO.class)
            .process(exchange -> {
                IncreaseStockDTO increaseStockDTO = exchange.getIn().getBody(IncreaseStockDTO.class);

                // convert to cancel payment DTO
                CancelOrderDTO cancelOrderDTO = new CancelOrderDTO(
                    increaseStockDTO.getOrderId()
                );

                exchange.getIn().setBody(cancelOrderDTO, CancelOrderDTO.class);
            })
            .marshal().json(JsonLibrary.Jackson)
            .to("direct:newOrder/cancelOrder");

        // ====================================================================================
        // Cancel payment step
        // ====================================================================================

        from("direct:newOrder/cancelPayment")
            .to(RabbitMQUriHelper.constructSagaUri(Exchange.PAYMENT, Queue.IN, RoutingKey.CANCEL_PAYMENT));

        // ====================================================================================
        // Revert decrease stock step
        // ====================================================================================

        from("direct:newOrder/revertDecreaseStock")
            .to(RabbitMQUriHelper.constructSagaUri(Exchange.INVENTORY, Queue.IN, RoutingKey.REVERT_DECREASED_STOCK));

        // ====================================================================================
        // Cancel order step
        // ====================================================================================

        from("direct:newOrder/cancelOrder")
            .to(RabbitMQUriHelper.constructSagaUri(Exchange.ORDER, Queue.IN, RoutingKey.CANCEL_ORDER));
    }

}

package fr.dopolytech.cdp.saga.infrastructure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class RabbitMQUriHelper {

    public static String constructSagaUri(RabbitMQUriHelper.Constants.Exchange exchange, RabbitMQUriHelper.Constants.Queue queueName, RabbitMQUriHelper.Constants.RoutingKey routingKey) {
        return "spring-rabbitmq:" + exchange.getName() + "?queues=" + queueName.getName(exchange) + "." + routingKey.getName() + "&routingKey=" + routingKey.getName() + "&autoDeclare=true";
    }

    public static class Constants {

        @RequiredArgsConstructor
        @Getter
        public enum Exchange {
            ORDER("order"), INVENTORY("inventory"), PAYMENT("payment"), SHIPPING("shipping");
            private final String name;
        }

        @RequiredArgsConstructor
        public enum Queue {
            IN("in"), OUT("out");

            private final String name;

            public String getName(RabbitMQUriHelper.Constants.Exchange exchange) {
                return exchange.getName() + "." + name;
            }
        }

        @RequiredArgsConstructor
        @Getter
        public enum RoutingKey {
            ORDER_CREATED("ORDER_CREATED"),
            CANCEL_ORDER("CANCEL_ORDER"),
            CONFIRM_ORDER("CONFIRM_ORDER"),
            DECREASE_STOCK("DECREASE_STOCK"),
            STOCK_DECREASED("STOCK_DECREASED"),
            DECREASE_STOCK_FAILED("DECREASE_STOCK_FAILED"),
            REVERT_DECREASED_STOCK("REVERT_DECREASED_STOCK"),
            CREATE_PAYMENT("CREATE_PAYMENT"),
            PAYMENT_CREATED("PAYMENT_CREATED"),
            PAYMENT_FAILED("PAYMENT_FAILED"),
            CANCEL_PAYMENT("CANCEL_PAYMENT"),
            SEND_ORDER("SEND_ORDER"),
            ORDER_SENT("ORDER_SENT"),
            SEND_ORDER_FAILED("SEND_ORDER_FAILED");

            private final String name;
        }

    }

}

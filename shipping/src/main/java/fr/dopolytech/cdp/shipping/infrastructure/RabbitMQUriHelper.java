package fr.dopolytech.cdp.shipping.infrastructure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class RabbitMQUriHelper {

    public static String constructSagaUri(Constants.Exchange exchange, Constants.Queue queueName, Constants.RoutingKey routingKey) {
        return "spring-rabbitmq:" + exchange.getName() + "?queues=" + queueName.getName(exchange) + "." + routingKey.getName() + "&routingKey=" + routingKey.getName() + "&autoDeclare=true";
    }

    public static class Constants {

        @RequiredArgsConstructor
        @Getter
        public enum Exchange {
            SHIPPING("shipping");
            private final String name;
        }

        @RequiredArgsConstructor
        public enum Queue {
            IN("in"), OUT("out");

            private final String name;

            public String getName(Exchange exchange) {
                return exchange.getName() + "." + name;
            }
        }

        @RequiredArgsConstructor
        @Getter
        public enum RoutingKey {
            SEND_ORDER("SEND_ORDER"),
            ORDER_SENT("ORDER_SENT"),
            SEND_ORDER_FAILED("SEND_ORDER_FAILED");

            private final String name;
        }

    }

}

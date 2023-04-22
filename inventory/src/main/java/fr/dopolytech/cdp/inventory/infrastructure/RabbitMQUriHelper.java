package fr.dopolytech.cdp.inventory.infrastructure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class RabbitMQUriHelper {

    public static String constructSagaUri(Constants.Exchange exchange, Constants.Queue queueName, Constants.RoutingKey routingKey) {
        return "spring-rabbitmq:" + exchange.getName() + "?queues=" + queueName.getName(exchange) + "&routingKey=" + routingKey.getName() + "&autoDeclare=true";
    }

    public static class Constants {

        @RequiredArgsConstructor
        @Getter
        public enum Exchange {
            INVENTORY("inventory");
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
            DECREASE_STOCK("DECREASE_STOCK"),
            STOCK_DECREASED("STOCK_DECREASED"),
            DECREASE_STOCK_FAILED("DECREASE_STOCK_FAILED"),
            REVERT_DECREASED_STOCK("REVERT_DECREASED_STOCK");

            private final String name;
        }

    }

}

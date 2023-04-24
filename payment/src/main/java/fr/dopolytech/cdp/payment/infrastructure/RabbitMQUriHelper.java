package fr.dopolytech.cdp.payment.infrastructure;

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
            PAYMENT("payment");
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
            CREATE_PAYMENT("CREATE_PAYMENT"),
            PAYMENT_CREATED("PAYMENT_CREATED"),
            PAYMENT_FAILED("PAYMENT_FAILED"),
            CANCEL_PAYMENT("CANCEL_PAYMENT");

            private final String name;
        }

    }

}

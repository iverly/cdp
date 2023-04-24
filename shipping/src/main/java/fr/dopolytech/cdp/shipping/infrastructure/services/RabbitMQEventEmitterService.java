package fr.dopolytech.cdp.shipping.infrastructure.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQEventEmitterService {

    private final CamelContext context;

    private final ObjectMapper objectMapper;

    public void emit(String routingKey, Object payload) {
        final String exchange = "shipping";
        ProducerTemplate template = this.context.createProducerTemplate();

        template.send(
            "spring-rabbitmq:" + exchange + "?queues=" + exchange + ".out&routingKey=" + routingKey + "&autoDeclare=true",
            new Processor(payload, objectMapper)
        );
    }

    private record Processor(Object payload, ObjectMapper objectMapper) implements org.apache.camel.Processor {

        @Override
        public void process(Exchange exchange) throws Exception {
            Message in = exchange.getIn();
            in.setBody(this.objectMapper.writeValueAsString(this.payload), String.class);
        }
    }

}

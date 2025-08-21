package orders.events;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class KafkaEventPublisher {
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    @Value("${app.kafka.topic}")
    private String topic;


    public void publish(OrderEvent event) {
        kafkaTemplate.send(topic, event.getOrderId().toString(), event);
    }
}

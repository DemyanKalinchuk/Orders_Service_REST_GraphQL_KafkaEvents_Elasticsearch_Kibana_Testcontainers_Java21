package orders.events;

import orders.elastic.OrderEventDocument;
import orders.elastic.OrderEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class OrderEventListener {
    private final OrderEventRepository repository;


    @KafkaListener(topics = "${app.kafka.topic}", groupId = "orders-indexer")
    public void onEvent(OrderEvent event) {
        var doc = OrderEventDocument.from(event);
        repository.save(doc);
    }
}

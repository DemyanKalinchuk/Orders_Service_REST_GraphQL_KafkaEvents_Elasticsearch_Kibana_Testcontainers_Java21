package orders.elastic;

import orders.events.OrderEvent;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import java.time.Instant;


@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
@Document(indexName = "order-events")
public class OrderEventDocument {
    @Id
    private String id; // use orderId + type
    private String type;
    private String orderId;
    private Instant occurredAt;


    public static OrderEventDocument from(OrderEvent e) {
        return OrderEventDocument.builder()
                .id(e.getOrderId() + ":" + e.getType())
                .type(e.getType())
                .orderId(e.getOrderId().toString())
                .occurredAt(e.getOccurredAt())
                .build();
    }
}

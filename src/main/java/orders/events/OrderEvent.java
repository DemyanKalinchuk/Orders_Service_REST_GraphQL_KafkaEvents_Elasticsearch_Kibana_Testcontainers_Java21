package orders.events;

import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private String type; // e.g., ORDER_CREATED
    private UUID orderId;
    private Instant occurredAt;
    private Object payload; // keep simple for demo
}

package orders.events;

import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private String type;       // e.g., ORDER_CREATED
    private UUID orderId;
    private Instant occurredAt;
    private Object payload;

    // ✅ static factory for created event
    public static OrderEvent created(UUID orderId, String email, BigDecimal amount) {
        return OrderEvent.builder()
                .type("ORDER_CREATED")
                .orderId(orderId)
                .occurredAt(Instant.now())
                .payload(Map.of(
                        "email", email,
                        "amount", amount
                ))
                .build();
    }

    public static OrderEvent paid(UUID orderId, BigDecimal amount) {
        return OrderEvent.builder()
                .type("ORDER_PAID")
                .orderId(orderId)
                .occurredAt(Instant.now())
                .payload(Map.of(
                        "amount", amount
                ))
                .build();
    }

    public static OrderEvent cancelled(UUID orderId, String reason) {
        return OrderEvent.builder()
                .type("ORDER_CANCELLED")
                .orderId(orderId)
                .occurredAt(Instant.now())
                .payload(Map.of(
                        "reason", reason
                ))
                .build();
    }
}

package orders.domain;

import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;


@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor
public class Order {
    private UUID id;
    private String customerEmail;
    private BigDecimal amount;
    private OrderStatus status;
    private Instant createdAt;
}

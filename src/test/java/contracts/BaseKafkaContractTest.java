package contracts;

import orders.events.OrderEvent;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;


@SpringBootTest
@ActiveProfiles("test")
public abstract class BaseKafkaContractTest {
    @Autowired KafkaTemplate<String, Object> kafkaTemplate;


    @BeforeEach void setup() {}


    public void triggerOrderCreated() {
        kafkaTemplate.send("orders.events", java.util.UUID.randomUUID().toString(),
                OrderEvent.created(java.util.UUID.randomUUID(), "qa@example.com", new java.math.BigDecimal("10.00")));
    }
}

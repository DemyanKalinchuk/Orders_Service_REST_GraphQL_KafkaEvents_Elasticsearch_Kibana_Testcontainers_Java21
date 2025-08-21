package orders.service;

import orders.domain.*;
import orders.dto.*;
import orders.events.KafkaEventPublisher;
import orders.events.OrderEvent;
import orders.repository.InMemoryOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.*;
import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final InMemoryOrderRepository repo = new InMemoryOrderRepository(); // swap with DB later
    private final KafkaEventPublisher publisher;


    public OrderResponse create(OrderRequest req) {
        var order = Order.builder()
                .id(UUID.randomUUID())
                .customerEmail(req.getCustomerEmail())
                .amount(req.getAmount() == null ? BigDecimal.ZERO : req.getAmount())
                .status(OrderStatus.CREATED)
                .createdAt(Instant.now())
                .build();
        repo.save(order);


        publisher.publish(OrderEvent.builder()
                .type("ORDER_CREATED")
                .orderId(order.getId())
                .occurredAt(Instant.now())
                .payload(Map.of("email", order.getCustomerEmail(), "amount", order.getAmount()))
                .build());


        return toResponse(order);
    }


    public Optional<OrderResponse> get(UUID id) {
        return repo.findById(id).map(this::toResponse);
    }


    public List<OrderResponse> list() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }


    private OrderResponse toResponse(Order o) {
        return OrderResponse.builder()
                .id(o.getId())
                .customerEmail(o.getCustomerEmail())
                .amount(o.getAmount())
                .status(o.getStatus().name())
                .createdAt(o.getCreatedAt())
                .build();
    }
}

package orders.repository;

import orders.domain.Order;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class InMemoryOrderRepository {
    private final Map<UUID, Order> store = new ConcurrentHashMap<>();


    public Order save(Order order) { store.put(order.getId(), order); return order; }
    public Optional<Order> findById(UUID id) { return Optional.ofNullable(store.get(id)); }
    public List<Order> findAll() { return new ArrayList<>(store.values()); }
}

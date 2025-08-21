package orders.gql;

import orders.dto.*;
import orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.*;
import org.springframework.stereotype.Controller;
import java.util.*;
import java.util.UUID;


@Controller
@RequiredArgsConstructor
public class OrderGraphQLController {
    private final OrderService service;


    @QueryMapping
    public OrderResponse order(@Argument UUID id) {
        return service.get(id).orElse(null);
    }


    @QueryMapping
    public List<OrderResponse> orders() {
        return service.list();
    }


    @MutationMapping
    public OrderResponse createOrder(@Argument("input") OrderRequest input) {
        return service.create(input);
    }
}

package orders.elastic;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface OrderEventRepository extends ElasticsearchRepository<OrderEventDocument, String> { }

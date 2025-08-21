package orderTests;

import orders.dto.OrderRequest;
import orders.elastic.OrderEventRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.net.URI;
import java.time.Duration;

import static graphql.Assert.assertTrue;
import static org.awaitility.Awaitility.await;
import static org.springframework.http.HttpStatus.OK;
import static org.testng.Assert.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class OrdersIntegrationTest {
    @Container
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.5.2"));


    @Container
    static ElasticsearchContainer elastic = new ElasticsearchContainer(
            DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch:8.13.0"))
            .withEnv("xpack.security.enabled", "false")
            .withEnv("discovery.type", "single-node");


    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry reg) {
        reg.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
        reg.add("spring.elasticsearch.uris", elastic::getHttpHostAddress);
    }


    @LocalServerPort
    int port;
    @Autowired
    TestRestTemplate rest;
    @Autowired
    OrderEventRepository eventRepo;


    @Test
    public void createOrder_rest_publishesEvent_indexedInElasticsearch() {
        var req = new OrderRequest("qa@example.com", new java.math.BigDecimal("99.50"));
        var response = rest.postForEntity(URI.create("http://localhost:"+port+"/api/orders"), req, String.class);
        assertEquals(OK, response.getStatusCode());


        await().atMost(Duration.ofSeconds(20)).untilAsserted(() -> {
            var all = eventRepo.findAll();
            assertTrue(all.iterator().hasNext(), "Expected at least one event indexed in ES");
        });
    }
}

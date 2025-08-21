package customerTests;

import orders.elastic.OrderEventRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.testcontainers.junit.jupiter.Testcontainers;


@SpringBootTest
@Testcontainers
@AutoConfigureStubRunner(ids = "com.example:orders-service:+:stubs", stubsMode = StubRunnerProperties.StubsMode.LOCAL)
class ContractConsumerTest {
    @Autowired
    OrderEventRepository repo;


    @Test
    void shouldConsumeOrderCreatedAndIndex() {
// When stubs run, they will emit the message as defined by the contract
// Then verify it is handled and indexed (use Awaitility)
        org.awaitility.Awaitility.await().until(() -> repo.count() > 0);
    }
}

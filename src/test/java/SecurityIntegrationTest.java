

import orders.TestJwt;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SecurityIntegrationTest {
    @LocalServerPort int port;
    @Autowired TestRestTemplate rest;


    @Test
    void restRequiresAuthTest() {
        var resp = rest.getForEntity("http://localhost:"+port+"/api/orders", String.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, resp.getStatusCode());
    }


    @Test
    void rest_allows_with_bearer() {
        HttpHeaders h = new HttpHeaders();
        h.setBearerAuth(TestJwt.token()); // implement TestJwt to sign with dev private key
        var resp = rest.exchange("http://localhost:"+port+"/api/orders", HttpMethod.GET, new HttpEntity<>(h), String.class);
        Assertions.assertNotEquals(HttpStatus.UNAUTHORIZED, resp.getStatusCode());
    }
}

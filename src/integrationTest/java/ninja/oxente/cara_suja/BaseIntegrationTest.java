package ninja.oxente.cara_suja;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.mongodb.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class BaseIntegrationTest {

    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(
        DockerImageName.parse("mongo:latest"));

    static {
        mongoDBContainer.start();
    }

    @LocalServerPort
    protected int port;
    @Autowired
    protected TestRestTemplate restTemplate;

    @DynamicPropertySource
    public static void overrideProperties(
        org.springframework.test.context.DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

}

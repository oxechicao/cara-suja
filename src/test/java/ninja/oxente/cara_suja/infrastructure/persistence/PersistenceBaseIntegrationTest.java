package ninja.oxente.cara_suja.infrastructure.persistence;

import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mongodb.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

@DataMongoTest
@Testcontainers
public class PersistenceBaseIntegrationTest {

    @Container
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(
        DockerImageName.parse("mongo:latest"));

    @DynamicPropertySource
    public static void overrideProperties(
        org.springframework.test.context.DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }
}

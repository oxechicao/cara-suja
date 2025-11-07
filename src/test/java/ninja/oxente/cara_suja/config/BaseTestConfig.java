package ninja.oxente.cara_suja.config;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.mongodb.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

public class BaseTestConfig {

    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(
        DockerImageName.parse("mongo:latest"));

    @BeforeAll
    static void beforeAll() {
        mongoDBContainer.start();
    }

    @AfterAll
    static void afterAll() {
        mongoDBContainer.stop();
    }

    @DynamicPropertySource
    public static void overrideProperties(
        org.springframework.test.context.DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }
}

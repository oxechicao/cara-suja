package ninja.oxente.cara_suja.config;

import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
    "de.flapdoodle.mongodb.embedded.version=4.21.0",
    "spring.data.mongodb.port=0",
    "spring.data.mongodb.host=localhost",
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.session.SessionAutoConfiguration"

})
@AutoConfigureDataMongo
public abstract class BaseTestConfig {

}

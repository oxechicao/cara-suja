package ninja.oxente.cara_suja.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import ninja.oxente.cara_suja.builders.UserEntityBuilder;
import ninja.oxente.cara_suja.infrastructure.persistence.entities.UserEntity;
import ninja.oxente.cara_suja.infrastructure.persistence.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataMongoTest
@Testcontainers
@DisplayName("User Repository Integration Tests")
public class UserRepositoryIntegrationTestPersistence extends PersistenceBaseIntegrationTest {

    final String password = "best-jedi-password";
    final Argon2PasswordEncoder argon2 = new Argon2PasswordEncoder(16, 32, 1, 60000, 10);
    final String encodedPasswd = argon2.encode(password);

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("SHOULD save user entity WHEN save is called")
    void shouldSaveUserEntityWhenSaveIsCalled() {
        UserEntity entity = new UserEntityBuilder()
            .name("Ahsoka Tano")
            .email("ahsoka.tano@temple.jedi")
            .password(encodedPasswd)
            .build();

        UserEntity response = this.userRepository.save(entity);
        assertNotNull(response.id());
    }
}

package ninja.oxente.cara_suja.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import ninja.oxente.cara_suja.BaseIntegrationTest;
import ninja.oxente.cara_suja.builders.UserEntityBuilder;
import ninja.oxente.cara_suja.builders.UserModelBuilder;
import ninja.oxente.cara_suja.domains.security.IPasswordHasher;
import ninja.oxente.cara_suja.domains.user.UserModel;
import ninja.oxente.cara_suja.infrastructure.persistence.entities.UserEntity;
import ninja.oxente.cara_suja.infrastructure.persistence.repositories.MongoUserRepository;
import ninja.oxente.cara_suja.infrastructure.persistence.repositories.UserRepositoryImpl;
import ninja.oxente.cara_suja.infrastructure.security.Argo2Hasher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DisplayName("User Repository Integration Tests")
public class UserRepositoryImplIntegrationTest extends BaseIntegrationTest {

    private final String password = "best-jedi-password";
    private final IPasswordHasher passwordHasher = new Argo2Hasher();
    private final String encodedPasswd = passwordHasher.encode(password);
    private final UserEntity entityMock = new UserEntityBuilder()
        .name("Ahsoka Tano")
        .email("ahsoka.tano@temple.jedi")
        .password(encodedPasswd)
        .build();

    @Autowired
    private MongoUserRepository mongoUserRepository;
    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl(mongoUserRepository, passwordHasher);
        mongoUserRepository.deleteAll();
    }

    private UserEntity addUser() {
        return mongoUserRepository.save(entityMock);
    }

    @Nested
    @DisplayName("Save User tests")
    class SaveUserTests {

        @Test
        @DisplayName("SHOULD save user entity WHEN save is called")
        void shouldSaveUserEntityWhenSaveIsCalled() {
            UserModel user = userRepository.save(
                new UserModelBuilder()
                    .name("Ahsoka Tano")
                    .email("ahsoka.tano@jedi.master")
                    .password(password)
                    .build()
            );

            assertNotNull(user);
            assertNotNull(user.id());
            assertEquals("Ahsoka Tano", user.name());
        }
    }

    @Nested
    @DisplayName("Find by Email tests")
    class FindByEmailTests {

        @Test
        @DisplayName("SHOULD return user entity WHEN findByEmail is called")
        void shouldReturnUserEntityWhenFindByEmailIsCalled() {
            String email = addUser().email();
            UserModel response = userRepository.findByEmail(email);
            assertNotNull(response);
            assertEquals(email, response.email());
        }

        @Test
        @DisplayName("SHOULD return null WHEN findByEmail is called with non-existing email")
        void shouldReturnNullWhenFindByEmailIsCalledWithNonExistingEmail() {
            UserModel response = userRepository.findByEmail("whatever@email.com");
            assertNull(response);
        }
    }

    @Nested
    @DisplayName("Find All Users")
    class FindAllUsersTests {

        @Test
        @DisplayName("SHOULD return all users WHEN findAll is called")
        void shouldReturnAllUsersWhenFindAllIsCalled() {
            addUser();
            List<UserModel> response = userRepository.findAll();
            assertNotNull(response);
            assertFalse(response.isEmpty());
        }

        @Test
        @DisplayName("SHOULD return empty list WHEN findAll is called and no users exist")
        void shouldReturnEmptyListWhenFindAllIsCalledAndNoUsersExist() {
            List<UserModel> response = userRepository.findAll();
            assertNotNull(response);
            assertEquals(0, response.size());
        }
    }

    @Nested
    @DisplayName("Find User By Id")
    class FindUserByIdTests {

        @Test
        @DisplayName("SHOULD return entity WHEN findById is called")
        void shouldReturnEntityWhenFindByIdIsCalled() {
            UserEntity savedEntity = addUser();
            UserModel response = userRepository.findById(savedEntity.id());
            assertNotNull(response);
            assertEquals(savedEntity.id(), response.id());
        }

        @Test
        @DisplayName("SHOULD return null WHEN findById is called with non-existing id")
        void shouldReturnNullWhenFindByIdIsCalledWithNonExistingId() {
            UserModel response = userRepository.findById("non-existing-id");
            assertNull(response);
        }
    }

    @Nested
    @DisplayName("Update User")
    class UpdateUserTests {

        @Test
        @DisplayName("SHOULD update user WHEN save is called with existing entity")
        void shouldUpdateWhenSaveIsCalledWithExistingEntity() {
            UserEntity savedEntity = addUser();
            UserModel userModel = new UserModelBuilder(savedEntity)
                .name("Ahsoka Tano Updated")
                .build();

            UserModel response = userRepository.update(userModel, savedEntity.id());
            assertNotNull(response);
            assertEquals("Ahsoka Tano Updated", response.name());
        }

        @Test
        @DisplayName("SHOULD return null WHEN update is called with non-existing entity")
        void shouldReturnNullWhenUpdateIsCalledWithNonExistingEntity() {
            UserModel userModel = new UserModelBuilder()
                .name("Non Existing User")
                .build();
            UserModel response = userRepository.update(userModel, "non-existing-id");
            assertNull(response);
        }
    }
}

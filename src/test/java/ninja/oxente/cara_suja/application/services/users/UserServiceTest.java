package ninja.oxente.cara_suja.application.services.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;
import ninja.oxente.cara_suja.application.mappers.UserDtoMapper;
import ninja.oxente.cara_suja.application.services.user.UserService;
import ninja.oxente.cara_suja.builders.RegisterNewUserRequestBuilder;
import ninja.oxente.cara_suja.builders.UserEntityBuilder;
import ninja.oxente.cara_suja.domains.security.IPasswordHasher;
import ninja.oxente.cara_suja.infrastructure.persistence.entities.UserEntity;
import ninja.oxente.cara_suja.infrastructure.persistence.mappers.UserPersistenceMapper;
import ninja.oxente.cara_suja.infrastructure.persistence.repositories.UserRepository;
import ninja.oxente.cara_suja.infrastructure.security.Argo2Hasher;
import ninja.oxente.cara_suja.presentation.dto.user.RegisterUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private final IPasswordHasher passwordHasher = new Argo2Hasher();
    private final UserDtoMapper userDtoMapper = new UserDtoMapper();
    private final UserPersistenceMapper userPersistenceMapper = new UserPersistenceMapper(
        passwordHasher);
    @Mock
    UserRepository repository;
    private UserService service;

    @BeforeEach
    void setUp() {
        this.service = new UserService(repository, userDtoMapper, userPersistenceMapper);
    }

    @Test
    @DisplayName("SHOULD call call repository with the UserModel")
    public void testCreateUser() {
        String password = "secret-jedi";
        String encodedPassword = passwordHasher.encode(password);

        RegisterUserRequest request = new RegisterNewUserRequestBuilder()
            .name("Ahsoka Tano")
            .email("ahsoka.tano@temple.jedi")
            .password(password)
            .serialKey("may-4th-be-with-you")
            .build();

        UserEntityBuilder entityBuilder = new UserEntityBuilder()
            .id(null)
            .name("Ahsoka Tano")
            .email("ahsoka.tano@temple.jedi")
            .password(encodedPassword);

        UserEntity entitySave = entityBuilder.build();
        UserEntity entityResponse = entityBuilder.id(UUID.randomUUID().toString())
            .build();

        when(repository.save(isA(UserEntity.class))).thenReturn(entityResponse);

        String result = this.service.createNewUser(request);

        verify(this.repository, times(1)).save(argThat(entity ->
            entity.name().equals(entitySave.name())
                && entity.email().equals(entitySave.email())
                && passwordHasher.verify(password, entity.password())
        ));

        assertEquals(result, entityResponse.id());
    }

}
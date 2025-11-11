package ninja.oxente.cara_suja.application.services.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import ninja.oxente.cara_suja.application.mappers.UserDtoMapper;
import ninja.oxente.cara_suja.application.services.user.UserService;
import ninja.oxente.cara_suja.builders.RegisterNewUserRequestBuilder;
import ninja.oxente.cara_suja.builders.UpdateUserRequestBuilder;
import ninja.oxente.cara_suja.builders.UserListBuilder;
import ninja.oxente.cara_suja.builders.UserModelBuilder;
import ninja.oxente.cara_suja.domains.exceptions.EntityNotFoundException;
import ninja.oxente.cara_suja.domains.repositories.UserRepository;
import ninja.oxente.cara_suja.domains.security.IPasswordHasher;
import ninja.oxente.cara_suja.domains.user.UserModel;
import ninja.oxente.cara_suja.infrastructure.security.Argo2Hasher;
import ninja.oxente.cara_suja.presentation.dto.user.RegisterUserRequest;
import ninja.oxente.cara_suja.presentation.dto.user.UpdateUserRequest;
import ninja.oxente.cara_suja.presentation.dto.user.UserList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Test")
class UserServiceTest {

    private final IPasswordHasher passwordHasher = new Argo2Hasher();
    private final UserDtoMapper userDtoMapper = new UserDtoMapper();

    @Mock
    UserRepository userRepository;

    private UserService service;

    @BeforeEach
    void setUp() {
        this.service = new UserService(userDtoMapper, userRepository);

    }

    @Nested
    @DisplayName("CreateNewUser tests")
    class CreateNewUserTest {

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

            UserModel requestModel = new UserModelBuilder(request).build();
            UserModel modelSaved = new UserModelBuilder(requestModel).id("saved-request-model")
                .build();

            when(userRepository.save(requestModel)).thenReturn(modelSaved);

            String result = service.createNewUser(request);
            verify(userRepository, times(1)).save(requestModel);
            assertEquals(result, modelSaved.id());
        }
    }


    @Nested
    @DisplayName("GetAllUsers tests")
    class GetAllUsersTest {

        @Test
        @DisplayName("SHOULD return a list of users WHEN find user on database")
        public void testGetAllUsers() {
            List<UserModel> entities = List.of(
                new UserModelBuilder()
                    .id(UUID.randomUUID().toString())
                    .name("Anakin Skywalker")
                    .email("anakin.skywalker@jedi.temple")
                    .password(passwordHasher.encode("i-am-the-chosen-one"))
                    .build(),
                new UserModelBuilder()
                    .id(UUID.randomUUID().toString())
                    .name("Obi-Wan Kenobi")
                    .email("obiwan.kenobi@jedi.master")
                    .password(passwordHasher.encode("hello-there"))
                    .build()
            );

            List<UserList> expectedUsersList = List.of(
                new UserListBuilder(entities.get(0)).build(),
                new UserListBuilder(entities.get(1)).build()
            );

            when(userRepository.findAll()).thenReturn(entities);
            List<UserList> usersList = service.getAllUsers();

            verify(userRepository, times(1)).findAll();
            assertEquals(usersList, expectedUsersList);
            assertEquals(2, usersList.size());
            assertEquals("Anakin Skywalker", usersList.get(0).name());
            assertEquals("Obi-Wan Kenobi", usersList.get(1).name());
        }

        @Test
        @DisplayName("SHOULD return an empty list WHEN no user is found on database")
        public void testGetAllUsersEmpty() {
            when(userRepository.findAll()).thenReturn(List.of());
            List<UserList> usersList = service.getAllUsers();
            verify(userRepository, times(1)).findAll();
            assertEquals(0, usersList.size());
        }
    }

    @Nested
    @DisplayName("UpdateUser tests")
    class UpdateUserTest {

        private final UpdateUserRequest updateUserRequest = new UpdateUserRequestBuilder()
            .name("Ahsoka Tano Updated")
            .build();

        private final UserModel existingUser = new UserModelBuilder()
            .id(UUID.randomUUID().toString())
            .name("Ahsoka Tano")
            .email("ahsoka.tano@jedi.master")
            .password(passwordHasher.encode("secret-jedi"))
            .build();

        private final String unkownUserId = UUID.randomUUID().toString();

        @Test
        @DisplayName("SHOULD throw exception WHEN user is not found")
        public void shouldThrowExceptionWhenUserNotFound() {
            UserModel requestModel = userDtoMapper.fromUpdateUserRequest(
                this.updateUserRequest);

            when(userRepository.update(requestModel, unkownUserId)).thenReturn(null);

            String message = assertThrowsExactly(EntityNotFoundException.class, () -> {
                service.updateUser(this.unkownUserId, this.updateUserRequest);
            }).getMessage();

            assertEquals("User not found", message);
            verify(userRepository, times(1)).update(requestModel, unkownUserId);
        }

        @Test
        @DisplayName("SHOULD update user WHEN user is found")
        public void shouldUpdateUserWhenUserIsFound() throws EntityNotFoundException {
            String userId = existingUser.id();
            UserModel userToSave = new UserModelBuilder(updateUserRequest).build();
            UserModel userSaved = new UserModelBuilder(existingUser)
                .name(this.updateUserRequest.name())
                .build();

            when(userRepository.update(userToSave, userId)).thenReturn(userSaved);

            UserList updatedUser = service.updateUser(userId, updateUserRequest);

            assertEquals(updatedUser.id(), userId);
            assertEquals(updateUserRequest.name(), updatedUser.name());
            assertEquals(existingUser.email(), updatedUser.email());
            verify(userRepository, times(1)).update(userToSave, userId);
        }
    }
}

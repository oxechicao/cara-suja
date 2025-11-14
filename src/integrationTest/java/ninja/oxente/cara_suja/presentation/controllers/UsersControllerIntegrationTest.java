package ninja.oxente.cara_suja.presentation.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ninja.oxente.cara_suja.BaseIntegrationTest;
import ninja.oxente.cara_suja.builders.RegisterNewUserRequestBuilder;
import ninja.oxente.cara_suja.builders.UpdateUserRequestBuilder;
import ninja.oxente.cara_suja.infrastructure.persistence.repositories.UserMongoRepository;
import ninja.oxente.cara_suja.presentation.dto.user.RegisterUserRequestDto;
import ninja.oxente.cara_suja.presentation.dto.user.UpdateUserRequestDto;
import ninja.oxente.cara_suja.presentation.dto.user.UserListDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsersControllerIntegrationTest extends BaseIntegrationTest {

    private final UserMongoRepository userMongoRepository;

    public UsersControllerIntegrationTest(@Autowired UserMongoRepository userMongoRepository) {
        this.userMongoRepository = userMongoRepository;
    }

    @BeforeAll
    void beforeAll() {
        userMongoRepository.deleteAll();
    }

    @Nested
    @DisplayName("GIVEN I want to test CRUD operations tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class CrudOperationsTests {

        private final String baseUrl = "/api/v1/users";

        private String responseUserId;

        @AfterAll
        void afterAll() {
            userMongoRepository.deleteAll();
        }

        @Test
        @Order(1)
        @DisplayName("SHOULD register new user WHEN valid inputs was send")
        void shouldRegisterNewUser() {
            RegisterUserRequestDto requestUser = new RegisterNewUserRequestBuilder()
                .name("Ahsoka Tano")
                .email("ahsoka.tano@jedi.temple")
                .password("best-jedi")
                .serialKey("this-is-my-key")
                .build();

            String response = restTemplate.postForObject(baseUrl, requestUser,
                String.class);
            assertNotNull(response);
            responseUserId = response;
        }

        @Test
        @Order(2)
        @DisplayName("SHOULD return a list of users WHEN users are present")
        void shouldReturnListOfUsers() {
            UserListDto[] response = restTemplate.getForObject(baseUrl, UserListDto[].class);
            assertNotNull(response);
            assertEquals(1, response.length);
            assertEquals(responseUserId, response[0].id());
        }

        @Test
        @Order(3)
        @DisplayName("SHOULD return user updated WHEN input is valid")
        void shouldReturnUserUpdated() {
            String updateUrl = baseUrl + "/" + responseUserId;

            UpdateUserRequestDto updateUserRequestDto = new UpdateUserRequestBuilder()
                .name("Ahsoka Tano Updated")
                .build();

            restTemplate.put(updateUrl, updateUserRequestDto);
            UserListDto updatedUser = restTemplate.getForObject(baseUrl, UserListDto[].class)[0];
            assertEquals("Ahsoka Tano Updated", updatedUser.name());
        }

        @Test
        @Order(4)
        @DisplayName("SHOULD return user by id WHEN user exists")
        void shouldReturnUserById() {
            String getUrl = baseUrl + "/" + responseUserId;

            UserListDto user = restTemplate.getForObject(getUrl, UserListDto.class);
            assertNotNull(user);
            assertEquals(responseUserId, user.id());
        }
    }
}

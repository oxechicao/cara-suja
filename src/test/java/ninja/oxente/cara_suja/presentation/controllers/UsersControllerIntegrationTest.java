package ninja.oxente.cara_suja.presentation.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import ninja.oxente.cara_suja.builders.RegisterNewUserRequestBuilder;
import ninja.oxente.cara_suja.builders.UserEntityBuilder;
import ninja.oxente.cara_suja.infrastructure.persistence.entities.UserEntity;
import ninja.oxente.cara_suja.infrastructure.persistence.repositories.UserRepository;
import ninja.oxente.cara_suja.presentation.dto.user.RegisterUserRequest;
import ninja.oxente.cara_suja.presentation.dto.user.UserList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UsersControllerIntegrationTest extends PresentationBaseIntegrationTest {

    private final String baseUrl = "/api/v1/users";

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("SHOULD register new user WHEN valid inputs was send")
    void testOkRegisterNewUser() throws Exception {
        RegisterUserRequest requestUser = new RegisterNewUserRequestBuilder()
            .name("Ahsoka Tano")
            .email("ahsoka.tano@jedi.temple")
            .password("best-jedi")
            .serialKey("this-is-my-key")
            .build();

        String response = this.restTemplate.postForObject(this.baseUrl, requestUser, String.class);
        assertNotNull(response);

        UserEntity user = this.userRepository.findAll().getFirst();

        assertNotNull(user);
        assertEquals(response, user.id());
    }

    @Test
    @DisplayName("SHOULD return a list of users")
    void testGetUsersList() throws Exception {
        this.addUsers();

        UserList[] response = this.restTemplate.getForObject(this.baseUrl, UserList[].class);
        assertNotNull(response);
        assertEquals(2, response.length);
    }

    private void addUsers() {
        UserEntity user1 = new UserEntityBuilder()
            .name("Ahsoka Tano")
            .email("ahsoka.tano@jedi.temple")
            .password("best-jedi")
            .build();
        UserEntity user2 = new UserEntityBuilder()
            .name("Anakin Skywalker")
            .email("anakin.skywalker@dark.force")
            .password("dark-side")
            .build();

        this.userRepository.saveAll(List.of(user1, user2));
    }
}

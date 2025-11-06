package ninja.oxente.cara_suja.presentation.controllers;

import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;
import ninja.oxente.cara_suja.application.services.user.UserService;
import ninja.oxente.cara_suja.builders.RegisterNewUserRequestBuilder;
import ninja.oxente.cara_suja.builders.UserListBuilder;
import ninja.oxente.cara_suja.presentation.dto.user.RegisterUserRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class UsersControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UserService userService;

    @Nested
    @DisplayName("POST /api/v1/users")
    class PostUser {

        @Test
        @DisplayName("SHOULD register new user WHEN valid inputs was send")
        void testOkRegisterNewUser() throws Exception {
            RegisterUserRequest requestUser = new RegisterNewUserRequestBuilder()
                .name("Ahsoka Tano")
                .email("ahsoka.tano@jedi.temple")
                .password("best-jedi")
                .serialKey("this-is-my-key")
                .build();

            String responseId = UUID.randomUUID().toString();

            when(userService.createNewUser(requestUser)).thenReturn(responseId);

            mvc.perform(
                    MockMvcRequestBuilders.post("/api/v1/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Ahsoka Tano",
                                    "email": "ahsoka.tano@jedi.temple",
                                    "password": "best-jedi",
                                    "serialKey": "this-is-my-key"
                                }
                            """)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(responseId));

            verify(userService, times(1)).createNewUser(requestUser);
        }

        @Test
        @DisplayName("SHOULD return Bad Request WHEN body is empty")
        void testErrorNoBodyContent() throws Exception {
            mvc.perform(
                    MockMvcRequestBuilders.post("/api/v1/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Required request body is missing"));
        }

        @Test
        @DisplayName("SHOULD return error WHEN fields are empty")
        void testErrorReturnErrorWhenFieldsEmpty() throws Exception {
            mvc.perform(
                    MockMvcRequestBuilders.post("/api/v1/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "",
                                    "email": "",
                                    "password": "",
                                    "serialKey": ""
                                }
                            """)
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").value("Invalid fields"))
                .andExpect(jsonPath("$.errors.name", hasItem("Name should not be empty")))
                .andExpect(jsonPath("$.errors.password", hasItem("Password should not be empty")))
                .andExpect(
                    jsonPath("$.errors.serialKey", hasItem("Serial Key should not be empty")))
                .andExpect(jsonPath("$.errors.email", hasItem("E-mail should not be empty")));
        }

        @Test
        @DisplayName("SHOULD return password less than 6 characters")
        void testErrorPasswordLessThenSixCharacter() throws Exception {
            mvc.perform(
                    MockMvcRequestBuilders.post("/api/v1/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Ahsoka Tano",
                                    "email": "ahsoka.tano@jedi.temple",
                                    "password": "12345",
                                    "serialKey": "serial-key"
                                }
                            """)
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").value("Invalid fields"))
                .andExpect(
                    jsonPath("$.errors.password").value("Password must be more than 5 characters"));
        }

        @Test
        @DisplayName("SHOULD return e-mail invalid WHEN not valid e-mail sent")
        void testErrorEmailNotValid() throws Exception {
            mvc.perform(
                    MockMvcRequestBuilders.post("/api/v1/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Ahsoka Tano",
                                    "email": "email-invald",
                                    "password": "ahsokatano",
                                    "serialKey": "serial-key"
                                }
                            """)
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").value("Invalid fields"))
                .andExpect(jsonPath("$.errors.email").value("E-mail not valid"));
        }

        @Test
        @DisplayName("SHOULD return error WHEN name contains invalid character")
        void testErrorNameWithCharactersNotValid() throws Exception {
            mvc.perform(
                    MockMvcRequestBuilders.post("/api/v1/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Ahsok@ Tano",
                                    "email": "ahsoka.tano@jedi.temple",
                                    "password": "123456",
                                    "serialKey": "serial-key"
                                }
                            """)
                )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message").value("Invalid fields"))
                .andExpect(jsonPath("$.errors.name").value(
                    "Name should contain only letters, digits, hyphens, quote or blank space"));
        }
    }

    @Nested
    @DisplayName("GET /api/v1/users")
    class GetUsers {

        @Test
        @DisplayName("SHOULD return a list of users WHEN find user on database")
        void testOkGetAllUsers() throws Exception {
            UserListBuilder builder = new UserListBuilder();
            when(userService.getAllUsers()).thenReturn(List.of(builder.build(), builder.build()));

            mvc.perform(
                    MockMvcRequestBuilders.get("/api/v1/users")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));

            verify(userService, times(1)).getAllUsers();
        }

        @Test
        @DisplayName("SHOULD return empty list WHEN no users found on database")
        void testOkNoUsersFound() throws Exception {
            mvc.perform(
                    MockMvcRequestBuilders.get("/api/v1/users")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

            verify(userService, times(1)).getAllUsers();
        }
    }
}
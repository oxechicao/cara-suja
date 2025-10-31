package ninja.oxente.cara_suja.controllers.users;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class UsersControllerTest {

    @Autowired
    private MockMvc mvc;

    @Nested
    @DisplayName("POST /api/v1/users")
    class PostUser {

        @Test
        @DisplayName("should register new user with valid inputs")
        void shouldRegisterNewUser() throws Exception {
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
                .andExpect(status().isCreated());
        }
        }
    @Test
    void postRegisterUser() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/users").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }
}
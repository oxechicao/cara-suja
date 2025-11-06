package ninja.oxente.cara_suja;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@DisplayName("Argon2 Password Validation")
public class Argo2Test {

    @Test
    @DisplayName("SHOULD validate argon2 functionality")
    public void givenRawPassword_whenEncodedWithArgon2_thenMatchesEncodedPassword() {
        String rawPassword = "Baeldung";
        Argon2PasswordEncoder arg2SpringSecurity = new Argon2PasswordEncoder(16, 32, 1, 60000, 10);
        String springBouncyHash = arg2SpringSecurity.encode(rawPassword);

        assertTrue(arg2SpringSecurity.matches(rawPassword, springBouncyHash));
    }
}

package ninja.oxente.cara_suja.infrastructure.security;

import ninja.oxente.cara_suja.domains.security.IPasswordHasher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Primary
@Qualifier("argo2Hasher")
public class Argo2Hasher implements IPasswordHasher {

    private final Argon2PasswordEncoder passwordEncoder = new Argon2PasswordEncoder(16, 32, 1,
        60000, 10);

    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);

    }

    @Override
    public boolean verify(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);

    }
}

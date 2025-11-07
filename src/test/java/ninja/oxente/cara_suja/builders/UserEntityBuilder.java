package ninja.oxente.cara_suja.builders;

import java.util.UUID;
import ninja.oxente.cara_suja.infrastructure.persistence.entities.UserEntity;
import ninja.oxente.cara_suja.infrastructure.security.Argo2Hasher;

public class UserEntityBuilder {

    private final Argo2Hasher passwordHasher = new Argo2Hasher();

    private String id = UUID.randomUUID().toString();
    private String name = "Ahsoka Tano";
    private String email = "ahsoka.tano@jedi.master";
    private String password = passwordHasher.encode("password");

    public UserEntityBuilder() {
    }

    public UserEntityBuilder(UserEntity entity) {
        this.id = entity.id();
        this.name = entity.name();
        this.email = entity.email();
        this.password = entity.password();
    }

    public UserEntity build() {
        return new UserEntity(id, name, email, password);
    }

    public UserEntityBuilder id(String value) {
        this.id = value;
        return this;
    }

    public UserEntityBuilder name(String value) {
        this.name = value;
        return this;
    }

    public UserEntityBuilder email(String value) {
        this.email = value;
        return this;
    }

    public UserEntityBuilder password(String value) {
        this.password = value;
        return this;
    }

}

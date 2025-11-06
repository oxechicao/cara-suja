package ninja.oxente.cara_suja.builders;

import ninja.oxente.cara_suja.infrastructure.persistence.entities.UserEntity;

public class UserEntityBuilder {

    private String id;
    private String name;
    private String email;
    private String password;

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

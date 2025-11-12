package ninja.oxente.cara_suja.builders;

import ninja.oxente.cara_suja.presentation.dto.user.RegisterUserRequestDto;

public class RegisterNewUserRequestBuilder {

    private String name;
    private String email;
    private String password;
    private String serialKey;

    public RegisterNewUserRequestBuilder() {
    }

    public RegisterNewUserRequestBuilder(RegisterUserRequestDto register) {
        this.name = register.name();
        this.email = register.email();
        this.password = register.password();
        this.serialKey = register.serialKey();
    }

    public RegisterUserRequestDto build() {
        return new RegisterUserRequestDto(name, email, password, serialKey);
    }

    public RegisterNewUserRequestBuilder name(String value) {
        this.name = value;
        return this;
    }

    public RegisterNewUserRequestBuilder email(String value) {
        this.email = value;
        return this;
    }

    public RegisterNewUserRequestBuilder password(String value) {
        this.password = value;
        return this;
    }

    public RegisterNewUserRequestBuilder serialKey(String value) {
        this.serialKey = value;
        return this;
    }
}

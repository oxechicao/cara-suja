package ninja.oxente.cara_suja.builders;

import ninja.oxente.cara_suja.presentation.dto.user.UpdateUserRequestDto;

public class UpdateUserRequestBuilder {

    private String name;
    private String email;
    private String password;

    public UpdateUserRequestBuilder() {
    }

    public UpdateUserRequestDto build() {
        return new UpdateUserRequestDto(
            this.name,
            this.email,
            this.password
        );
    }

    public UpdateUserRequestBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UpdateUserRequestBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UpdateUserRequestBuilder password(String password) {
        this.password = password;
        return this;
    }
}

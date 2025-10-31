package ninja.oxente.cara_suja.validations;

import jakarta.validation.constraints.NotBlank;

public record PostUserRequest(
    @NotBlank(message = "Name should not be empty")
    String name,

    @NotBlank(message = "E-mail should not be empty")
    String email,

    @NotBlank(message = "Password should not be empty")
    String password,

    @NotBlank(message = "Serial Key should not be empty")
    String serialKey
) {

}

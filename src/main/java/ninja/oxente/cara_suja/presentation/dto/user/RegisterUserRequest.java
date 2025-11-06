package ninja.oxente.cara_suja.presentation.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record RegisterUserRequest(
    @Pattern(message = "Name should contain only letters, digits, hyphens, quote or blank space", regexp = "([a-zA-Z' -])+")
    @NotBlank(message = "Name should not be empty")
    String name,

    @NotBlank(message = "E-mail should not be empty")
    @Email(message = "E-mail not valid")
    String email,

    @NotBlank(message = "Password should not be empty")
    @Length(min = 6, message = "Password must be more than 5 characters")
    String password,

    @NotBlank(message = "Serial Key should not be empty")
    String serialKey
) {

}

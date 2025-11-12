package ninja.oxente.cara_suja.presentation.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record UpdateUserRequestDto(
    @Schema(example = "Anakin Skywalker")
    @Pattern(message = "Name should contain only letters, digits, hyphens, quote or blank space", regexp = "([a-zA-Z' -])+")
    String name,

    @Schema(example = "anakin.skywalker@dark.side")
    @Email(message = "E-mail not valid")
    String email,

    @Schema(example = "new-strong-password")
    @Length(min = 6, message = "Password must be more than 5 characters")
    String password
) {

}

package ninja.oxente.cara_suja.presentation.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateUserRequest(
    @Schema(example = "Anakin Skywalker") String name,
    @Schema(example = "anakin.skywalker@dark.side") String email,
    @Schema(example = "new-strong-password") String password
) {

}

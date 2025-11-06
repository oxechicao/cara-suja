package ninja.oxente.cara_suja.presentation.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public record ErrorResponse(
    @Schema(example = "Not found")
    String message,
    Optional<Map<String, List<String>>> errors
) {

}
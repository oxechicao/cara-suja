package ninja.oxente.cara_suja.presentation.dto.common;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public record ErrorResponse(
    String message,
    Optional<Map<String, List<String>>> errors
) {

}
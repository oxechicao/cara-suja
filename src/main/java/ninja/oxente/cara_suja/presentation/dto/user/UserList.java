package ninja.oxente.cara_suja.presentation.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import ninja.oxente.cara_suja.domains.karteira.KarteiraModel;

public record UserList(
    @Schema(example = "60a7e1f7a4b6c3d2e1f0a9b8")
    String id,
    @Schema(example = "Ahsoka Tano")
    String name,
    @Schema(example = "ahsoka.tano@jedi.master")
    String email,
    List<KarteiraModel> karteiras
) {

}

package ninja.oxente.cara_suja.domains.karteira;

import io.swagger.v3.oas.annotations.media.Schema;

public record KarteiraModel(
    @Schema(example = "60a7e1f7a4b6c3d2e1f0a9b8") String id,
    @Schema(example = "Contas da casa") String name,
    @Schema(example = "10000") int limit,
    @Schema(example = "5000") int goal,
    @Schema(example = "EDITOR", implementation = RoleEnum.class) RoleEnum role
) {

}

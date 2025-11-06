package ninja.oxente.cara_suja.domains.karteira;

import java.util.UUID;

public record KarteiraModel(UUID id, String name, int limit, int goal, RoleEnum role) {

}

package ninja.oxente.cara_suja.domains.user;

import java.util.List;
import java.util.UUID;
import ninja.oxente.cara_suja.domains.karteira.KarteiraModel;

public record UserModel(
    UUID id,
    String name,
    String email,
    String password,
    List<KarteiraModel> karteiras
) {

}

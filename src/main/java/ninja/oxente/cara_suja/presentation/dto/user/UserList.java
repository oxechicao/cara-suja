package ninja.oxente.cara_suja.presentation.dto.user;

import java.util.List;
import ninja.oxente.cara_suja.domains.karteira.KarteiraModel;

public record UserList(
    String id,
    String name,
    String email,
    List<KarteiraModel> karteiras
) {

}

package ninja.oxente.cara_suja.models;

import ninja.oxente.cara_suja.enums.RoleEnum;

import java.util.List;
import java.util.UUID;

public record UserModel(UUID id, String email, String password, RoleEnum role, List<UserModel> guests, List<KarteiraModel> karteiraModels) {

}

package ninja.oxente.cara_suja.builders;

import java.util.List;
import ninja.oxente.cara_suja.domains.karteira.KarteiraModel;
import ninja.oxente.cara_suja.domains.user.UserModel;
import ninja.oxente.cara_suja.presentation.dto.user.UserList;

public class UserListBuilder {

    private String id;
    private String name;
    private String email;
    private List<KarteiraModel> karteiras;

    public UserListBuilder() {
    }

    public UserListBuilder(UserModel value) {
        id = value.id();
        name = value.name();
        email = value.email();
    }

    public UserList build() {
        return new UserList(id, name, email, karteiras);
    }

    void id(String value) {
        id = value;
    }

    void name(String value) {
        name = value;
    }

    void email(String value) {
        email = value;
    }

    void karteiras(List<KarteiraModel> value) {
        karteiras = value;
    }
}

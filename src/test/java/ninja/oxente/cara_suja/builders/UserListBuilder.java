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

    public UserListBuilder id(String value) {
        id = value;
        return this;
    }

    public UserListBuilder name(String value) {
        name = value;
        return this;
    }

    public UserListBuilder email(String value) {
        email = value;
        return this;
    }

    public UserListBuilder karteiras(List<KarteiraModel> value) {
        karteiras = value;
        return this;
    }
}

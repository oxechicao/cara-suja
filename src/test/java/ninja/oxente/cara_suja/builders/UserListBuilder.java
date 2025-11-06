package ninja.oxente.cara_suja.builders;

import java.util.List;
import ninja.oxente.cara_suja.domains.karteira.KarteiraModel;
import ninja.oxente.cara_suja.infrastructure.persistence.entities.UserEntity;
import ninja.oxente.cara_suja.presentation.dto.user.UserList;

public class UserListBuilder {

    private String id;
    private String name;
    private String email;
    private List<KarteiraModel> karteiras;

    public UserListBuilder() {
    }

    public UserListBuilder(UserEntity entity) {
        this.id = entity.id();
        this.name = entity.name();
        this.email = entity.email();
    }

    public UserList build() {
        return new UserList(
            id,
            name,
            email,
            karteiras
        );
    }

    void id(String value) {
        this.id = value;
    }

    void name(String value) {
        this.name = value;
    }

    void email(String value) {
        this.email = value;
    }

    void karteiras(List<KarteiraModel> value) {
        this.karteiras = value;
    }
}

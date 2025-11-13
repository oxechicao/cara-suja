package ninja.oxente.cara_suja.builders;

import java.util.List;
import java.util.UUID;
import ninja.oxente.cara_suja.domains.karteira.KarteiraModel;
import ninja.oxente.cara_suja.domains.user.UserModel;
import ninja.oxente.cara_suja.infrastructure.persistence.entities.UserEntity;
import ninja.oxente.cara_suja.presentation.dto.user.RegisterUserRequestDto;
import ninja.oxente.cara_suja.presentation.dto.user.UpdateUserRequestDto;

public class UserModelBuilder {

    private String id;
    private String name;
    private String email;
    private String password;
    private List<KarteiraModel> karteiras;

    public UserModelBuilder() {
    }

    public UserModelBuilder(UpdateUserRequestDto request) {
        this.id = null;
        this.name = request.name();
        this.email = request.email();
        this.password = request.password();
        this.karteiras = null;
    }

    public UserModelBuilder(UserModel model) {
        this.id = model.id();
        this.name = model.name();
        this.email = model.email();
        this.password = model.password();
        this.karteiras = model.karteiras();
    }


    public UserModelBuilder(RegisterUserRequestDto request) {
        this.name = request.name();
        this.email = request.email();
        this.password = request.password();
        this.karteiras = null;
    }

    public UserModelBuilder(UserEntity entity) {
        this.id = entity.id();
        this.name = entity.name();
        this.email = entity.email();
        this.password = entity.password();
        this.karteiras = null;
    }

    public UserModelBuilder id(String value) {
        this.id = value;
        return this;
    }

    public UserModelBuilder name(String value) {
        this.name = value;
        return this;
    }

    public UserModelBuilder email(String value) {
        this.email = value;
        return this;
    }

    public UserModelBuilder password(String value) {
        this.password = value;
        return this;
    }

    public UserModelBuilder karteiras(List<KarteiraModel> value) {
        this.karteiras = value;
        return this;
    }

    public UserModel build() {
        return new UserModel(id, name, email, password, karteiras);
    }

    public UserModel caseAhsokaTano(boolean created) {
        if (created) {
            id(UUID.randomUUID().toString());
        } else {
            id(null);
        }

        name("Ahsoka Tano");
        email("ahsoka.tano@jedi.master");
        password("secret-jedi");

        return build();
    }
}

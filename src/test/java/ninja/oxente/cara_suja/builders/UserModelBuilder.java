package ninja.oxente.cara_suja.builders;

import java.util.List;
import java.util.UUID;
import ninja.oxente.cara_suja.domains.karteira.KarteiraModel;
import ninja.oxente.cara_suja.domains.user.UserModel;

public class UserModelBuilder {

    private UUID id;
    private String name;
    private String email;
    private String password;
    private List<KarteiraModel> karteiras;

    public UserModelBuilder() {
    }

    public UserModelBuilder(UserModel model) {
        this.id = model.id();
        this.name = model.name();
        this.email = model.email();
        this.password = model.password();
        this.karteiras = model.karteiras();
    }

    public UserModelBuilder id(UUID value) {
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

}

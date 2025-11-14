package ninja.oxente.cara_suja.infrastructure.persistence.mappers;

import ninja.oxente.cara_suja.domains.user.UserModel;
import ninja.oxente.cara_suja.infrastructure.persistence.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {

    public UserPersistenceMapper() {
    }

    public UserEntity mapModelToEntity(UserModel model) {
        String id = (model.id() != null) ? model.id() : null;
        return new UserEntity(id, model.name(), model.email(), model.password());
    }

    public UserModel mapEntityToModel(UserEntity entity) {
        return new UserModel(
            entity.id(),
            entity.name(),
            entity.email(),
            entity.password(),
            null
        );
    }

    public UserEntity mergeModelToEntity(UserModel model, UserEntity entity) {
        String name = (model.name() != null) ? model.name() : entity.name();
        String email = (model.email() != null) ? model.email() : entity.email();
        String password = model.password();

        return new UserEntity(entity.id(), name, email, password);
    }
}

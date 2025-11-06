package ninja.oxente.cara_suja.infrastructure.persistence.mappers;

import ninja.oxente.cara_suja.domains.security.IPasswordHasher;
import ninja.oxente.cara_suja.domains.user.UserModel;
import ninja.oxente.cara_suja.infrastructure.persistence.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceMapper {

    private final IPasswordHasher passwordHasher;

    public UserPersistenceMapper(IPasswordHasher passwordHasher) {
        this.passwordHasher = passwordHasher;
    }

    public UserEntity mapModelToEntity(UserModel model) {
        String id = (model.id() != null) ? model.id() : null;
        return new UserEntity(id, model.name(), model.email(),
            this.passwordHasher.encode(model.password()));
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
}

package ninja.oxente.cara_suja.infrastructure.persistence.repositories;

import java.util.List;
import java.util.Optional;
import ninja.oxente.cara_suja.domains.repositories.UserRepository;
import ninja.oxente.cara_suja.domains.security.IPasswordHasher;
import ninja.oxente.cara_suja.domains.user.UserModel;
import ninja.oxente.cara_suja.infrastructure.persistence.entities.UserEntity;
import ninja.oxente.cara_suja.infrastructure.persistence.mappers.UserPersistenceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
@Qualifier("infra-userRepository")
public class UserRepositoryImpl implements UserRepository {

    private final MongoUserRepository mongo;

    private final IPasswordHasher passwordHasher;

    private final UserPersistenceMapper mapper;

    public UserRepositoryImpl(MongoUserRepository mongo,
        @Autowired IPasswordHasher passwordHasher) {
        this.mongo = mongo;
        this.passwordHasher = passwordHasher;
        this.mapper = new UserPersistenceMapper(passwordHasher);
    }

    @Override
    public UserModel findById(String id) {
        Optional<UserEntity> entity = mongo.findById(id);
        return entity.map(mapper::mapEntityToModel).orElse(null);
    }

    @Override
    public List<UserModel> findAll() {
        List<UserEntity> entities = mongo.findAll();
        return entities.stream()
            .map(mapper::mapEntityToModel)
            .toList();
    }

    @Override
    public UserModel save(UserModel userModel) {
        UserEntity entity = mapper.mapModelToEntity(userModel);
        UserEntity savedEntity = mongo.save(entity);
        return mapper.mapEntityToModel(savedEntity);
    }

    @Override
    public UserModel update(UserModel userModel, String id) {
        Optional<UserEntity> userEntity = mongo.findById(id);
        if (userEntity.isEmpty()) {
            return null;
        }

        UserEntity entity = mapper.mergeModelToEntity(userModel, userEntity.get());
        UserEntity savedEntity = mongo.save(entity);
        return mapper.mapEntityToModel(savedEntity);
    }

    @Override
    public UserModel findByEmail(String email) {
        Optional<UserEntity> userEntity = mongo.findByEmail(email);
        return userEntity.map(mapper::mapEntityToModel).orElse(null);

    }
}

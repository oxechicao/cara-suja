package ninja.oxente.cara_suja.infrastructure.persistence.repositories;

import java.util.Optional;
import ninja.oxente.cara_suja.infrastructure.persistence.entities.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMongoRepository extends MongoRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(String email);
}

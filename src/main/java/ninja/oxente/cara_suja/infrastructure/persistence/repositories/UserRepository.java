package ninja.oxente.cara_suja.infrastructure.persistence.repositories;

import ninja.oxente.cara_suja.infrastructure.persistence.entities.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    UserEntity findByEmail(String email);
}

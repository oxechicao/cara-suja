package ninja.oxente.cara_suja.infrastructure.persistence.repositories;

import ninja.oxente.cara_suja.infrastructure.persistence.entities.KarteiraEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface KarteiraMongoRepository extends MongoRepository<KarteiraEntity, String> {

}

package ninja.oxente.cara_suja.infrastructure.persistence.repositories;

import ninja.oxente.cara_suja.domains.karteira.KarteiraModel;
import ninja.oxente.cara_suja.infrastructure.persistence.entities.KarteiraEntity;
import ninja.oxente.cara_suja.infrastructure.persistence.mappers.KarteiraPersistenceMapper;
import org.springframework.stereotype.Repository;

@Repository
public class KarteiraRepository extends BaseRepositoryImpl<
    KarteiraModel,
    KarteiraEntity,
    KarteiraMongoRepository,
    KarteiraPersistenceMapper> {

    public KarteiraRepository(
        KarteiraMongoRepository repository,
        KarteiraPersistenceMapper mapper
    ) {
        super(repository, mapper);
    }

}

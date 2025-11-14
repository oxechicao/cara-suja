package ninja.oxente.cara_suja.infrastructure.persistence.mappers;

import ninja.oxente.cara_suja.domains.karteira.KarteiraModel;
import ninja.oxente.cara_suja.infrastructure.persistence.entities.KarteiraEntity;
import org.springframework.stereotype.Component;

@Component
public class KarteiraPersistenceMapper implements BaseMapper<KarteiraModel, KarteiraEntity> {

    public KarteiraModel toModel(KarteiraEntity entity) {
        return new KarteiraModel(
            entity.id(),
            entity.name(),
            entity.limit(),
            entity.goal(),
            null
        );
    }

    public KarteiraEntity fromModel(KarteiraModel model) {
        return new KarteiraEntity(
            model.id(),
            model.name(),
            model.limit(),
            model.goal()
        );
    }

    public KarteiraEntity mergeModelToEntity(KarteiraModel model, KarteiraEntity entity) {
        String name = (model.name() != null) ? model.name() : entity.name();
        Integer limit = (model.limit() != null) ? model.limit() : entity.limit();
        Integer goal = (model.goal() != null) ? model.goal() : entity.goal();
        return new KarteiraEntity(entity.id(), name, limit, goal);
    }

}

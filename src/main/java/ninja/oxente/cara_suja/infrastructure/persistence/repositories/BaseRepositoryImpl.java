package ninja.oxente.cara_suja.infrastructure.persistence.repositories;

import java.util.List;
import java.util.Optional;
import ninja.oxente.cara_suja.domains.repositories.BaseRepository;
import ninja.oxente.cara_suja.infrastructure.persistence.mappers.BaseMapper;
import org.springframework.data.repository.CrudRepository;

public abstract class BaseRepositoryImpl<M, E,
    R extends CrudRepository<E,
        String>,
    P extends BaseMapper<M, E>> implements BaseRepository<M> {

    protected final R repository;
    protected final P mapper;

    public BaseRepositoryImpl(R r, P m) {
        this.repository = r;
        this.mapper = m;
    }

    @Override
    public M findById(String id) {
        Optional<E> entity = repository.findById(id);
        return entity.map(mapper::toModel).orElse(null);
    }

    @Override
    public List<M> findAll() {
        List<E> entities = (List<E>) repository.findAll();
        return entities.stream().map(mapper::toModel).toList();
    }

    @Override
    public M save(M m) {
        E entity = mapper.fromModel(m);
        E savedEntity = repository.save(entity);
        return mapper.toModel(savedEntity);
    }

    @Override
    public M update(M m, String id) {
        Optional<E> existingEntity = repository.findById(id);
        if (existingEntity.isEmpty()) {
            return null;
        }

        E mergedEntity = mapper.mergeModelToEntity(m, existingEntity.get());
        E updatedEntity = repository.save(mergedEntity);
        return mapper.toModel(updatedEntity);
    }
}

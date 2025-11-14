package ninja.oxente.cara_suja.infrastructure.persistence.mappers;

public interface BaseMapper<M, E> {

    M toModel(E entity);

    E fromModel(M model);

    E mergeModelToEntity(M model, E entity);

}

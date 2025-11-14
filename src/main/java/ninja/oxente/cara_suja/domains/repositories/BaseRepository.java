package ninja.oxente.cara_suja.domains.repositories;

import java.util.List;

public interface BaseRepository<T> {

    T findById(String id);

    List<T> findAll();

    T save(T t);

    T update(T t, String id);
}

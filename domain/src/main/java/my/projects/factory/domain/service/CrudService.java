package my.projects.factory.domain.service;

import java.util.List;
import java.util.Optional;

/**
 * Generic CRUD service interface defining basic operations
 * for managing entities of type {@code T} identified by {@code ID}.
 *
 * @param <T>  the type of the entity/model
 * @param <ID> the type of the entity identifier
 */
public interface CrudService<T, ID> {

    List<T> findAll();

    Optional<T> findById(ID id);

    T create(T entity);

    T update(ID id, T entity);

    void delete(ID id);
}

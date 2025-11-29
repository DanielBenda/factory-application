package my.projects.factory.domain.service.serviceImpl;

import org.springframework.data.repository.CrudRepository;
import my.projects.factory.domain.service.CrudService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Generic CRUD service implementation for any entity-model pair.
 * <p>
 * Provides basic CRUD operations using a {@link CrudRepository}
 * and mapper functions to convert between entity and domain model.
 *
 * @param <M>  the domain model type
 * @param <E>  the entity type
 * @param <ID> the type of the entity identifier
 */
public abstract class CrudServiceImpl<M, E, ID> implements CrudService<M, ID> {

    private final CrudRepository<E, ID> repository;
    private final Function<E, M> toModel;
    private final Function<M, E> toEntity;

    /**
     * Constructs a new CrudServiceImpl.
     *
     * @param repository the Spring Data repository for the entity
     * @param toModel    function to convert entity to model
     * @param toEntity   function to convert model to entity
     */
    protected CrudServiceImpl(CrudRepository<E, ID> repository, Function<E, M> toModel, Function<M, E> toEntity) {
        this.repository = repository;
        this.toModel = toModel;
        this.toEntity = toEntity;
    }

    @Override
    public List<M> findAll() {
        List<M> models = new ArrayList<>();
        repository.findAll().forEach(e -> models.add(toModel.apply(e)));
        return models;
    }

    @Override
    public Optional<M> findById(ID id) {
        return repository.findById(id).map(toModel);
    }

    @Override
    public M create(M model) {
        return toModel.apply(repository.save(toEntity.apply(model)));
    }

    @Override
    public M update(ID id, M model) {
        E entity = toEntity.apply(model);
        return toModel.apply(repository.save(entity));
    }

    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }
}

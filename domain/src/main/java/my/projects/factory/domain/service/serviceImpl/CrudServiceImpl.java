package my.projects.factory.domain.service.serviceImpl;

import org.springframework.data.repository.CrudRepository;
import my.projects.factory.domain.service.CrudService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class CrudServiceImpl<M, E, ID> implements CrudService<M, ID> {

    private final CrudRepository<E, ID> repository;
    private final Function<E, M> toModel;
    private final Function<M, E> toEntity;

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

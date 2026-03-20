package my.projects.factory.domain.service.serviceImpl;

import my.projects.factory.domain.service.PageableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.Function;

/**
 * Base service implementation providing pagination support.
 * <p>
 * Extends {@link CrudServiceImpl} with a generic {@link Page}-based
 * read operation backed by a {@link JpaRepository}.
 *
 * @param <M>  domain model type
 * @param <E>  JPA entity type
 * @param <ID> entity identifier type
 */
public abstract class PageableServiceImpl<M, E, ID>
        extends CrudServiceImpl<M, E, ID>
        implements PageableService<M, ID> {

    protected final JpaRepository<E, ID> repository;
    protected final Function<E, M> toModel;

    /**
     * Constructs a new CrudServiceImpl.
     *
     * @param repository the Spring Data repository for the entity
     * @param toModel    function to convert entity to model
     * @param toEntity   function to convert model to entity
     */
    protected PageableServiceImpl(
            JpaRepository<E, ID> repository,
            Function<E, M> toModel,
            Function<M, E> toEntity
    ) {
        super(repository, toModel, toEntity);
        this.repository = repository;
        this.toModel = toModel;
    }

    /**
     * Returns a paginated list of domain models.
     *
     * @param pageable pagination parameters
     * @return page of mapped domain models
     */
    @Override
    public Page<M> findPage(Pageable pageable) {
        return repository.findAll(pageable).map(toModel);
    }
}

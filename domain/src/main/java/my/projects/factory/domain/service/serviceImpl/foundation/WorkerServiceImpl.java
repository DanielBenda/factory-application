package my.projects.factory.domain.service.serviceImpl.foundation;

import my.projects.factory.domain.filter.foundation.WorkerFilter;
import my.projects.factory.domain.mapper.foundation.WorkerMapper;
import my.projects.factory.domain.model.foundation.WorkerModel;
import my.projects.factory.domain.service.foundation.WorkerService;
import my.projects.factory.domain.service.serviceImpl.PageableServiceImpl;
import my.projects.factory.domain.service.support.FilterExecutor;
import my.projects.factory.persistence.entity.foundation.Worker;
import my.projects.factory.persistence.repository.foundation.WorkerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Implementation of {@link WorkerService} that handles business logic
 * for Worker entities. Uses {@link WorkerMapper} to convert between
 * {@link Worker} entities and {@link WorkerModel} domain models.
 */
@Service
public class WorkerServiceImpl
        extends PageableServiceImpl<WorkerModel, Worker, UUID>
        implements WorkerService {

    private final WorkerRepository repository;
    private final WorkerMapper mapper;

    protected WorkerServiceImpl(WorkerRepository repository, WorkerMapper mapper) {
        super(repository, mapper::toModel, mapper::toEntity);
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Returns a paginated list of {@link WorkerModel} filtered by the provided criteria.
     * <p>
     * If no filter is provided, all operation types are returned.
     * If a filter is present, the method performs a case-insensitive partial match
     * (contains) on either the {@code name} or {@code surname} field.
     * </p>
     *
     * @param filter   optional filter containing search criteria (nameQuery or surnameQuery)
     * @param pageable pagination information (page number, size, sorting)
     * @return page of filtered and mapped {@link WorkerModel} instances
     */
    @Override
    public Page<WorkerModel> findWorkers(WorkerFilter filter, Pageable pageable) {

        Map<String, String> filters = new HashMap<>();

        if (filter != null) {
            filters.put("name", filter.nameQuery());
            filters.put("surname", filter.surnameQuery());
        }

        Map<String, Function<String, Supplier<Page<Worker>>>> queryMap = Map.of(
                "name", value -> () -> repository.findByNameContainingIgnoreCase(value, pageable),
                "surname", value -> () -> repository.findBySurnameContainingIgnoreCase(value, pageable)
        );

        return FilterExecutor.execute(
                filters,
                queryMap,
                () -> repository.findAll(pageable),
                mapper::toModel
        );
    }
}
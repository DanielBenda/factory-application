package my.projects.factory.domain.service.serviceImpl.workflow;

import my.projects.factory.domain.filter.workflow.PartFilter;
import my.projects.factory.domain.mapper.workflow.PartMapper;
import my.projects.factory.domain.model.workflow.PartModel;
import my.projects.factory.domain.service.serviceImpl.PageableServiceImpl;
import my.projects.factory.domain.service.support.FilterExecutor;
import my.projects.factory.domain.service.workflow.PartService;
import my.projects.factory.persistence.entity.workflow.Part;
import my.projects.factory.persistence.repository.workflow.PartRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Implementation of {@link PartService} that handles business logic
 * for Part entities. Uses {@link PartMapper} to convert between
 * {@link Part} entities and {@link PartModel} domain models.
 */
@Service
public class PartServiceImpl
        extends PageableServiceImpl<PartModel, Part, UUID>
        implements PartService {

    private final PartRepository partRepository;
    private final PartMapper mapper;

    protected PartServiceImpl(PartRepository repository, PartMapper mapper) {
        super(repository, mapper::toModel, mapper::toEntity);
        this.partRepository = repository;
        this.mapper = mapper;
    }

    /**
     * Returns a paginated list of {@link PartModel} filtered by the provided criteria.
     * <p>
     * If no filter is provided, all operation types are returned.
     * If a filter is present, the method performs a case-insensitive partial match
     * (contains) on either the {@code name} or {@code code} field.
     * </p>
     *
     * @param filter   optional filter containing search criteria (nameQuery or codeQuery)
     * @param pageable pagination information (page number, size, sorting)
     * @return page of filtered and mapped {@link PartModel} instances
     */
    @Override
    public Page<PartModel> findParts(PartFilter filter, Pageable pageable) {

        Map<String, String> filters = new HashMap<>();

        if (filter != null) {
            filters.put("name", filter.nameQuery());
            filters.put("code", filter.codeQuery());
        }

        Map<String, Function<String, Supplier<Page<Part>>>> queryMap = Map.of(
                "name", value -> () -> partRepository.findByNameContainingIgnoreCase(value, pageable),
                "code", value -> () -> partRepository.findByCodeContainingIgnoreCase(value, pageable)
        );

        return FilterExecutor.execute(
                filters,
                queryMap,
                () -> partRepository.findAll(pageable),
                mapper::toModel
        );
    }
}

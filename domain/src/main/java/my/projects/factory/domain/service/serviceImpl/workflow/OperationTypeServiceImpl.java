package my.projects.factory.domain.service.serviceImpl.workflow;

import my.projects.factory.domain.filter.workflow.OperationTypeFilter;
import my.projects.factory.domain.mapper.workflow.OperationTypeMapper;
import my.projects.factory.domain.model.workflow.OperationTypeModel;
import my.projects.factory.domain.service.serviceImpl.PageableServiceImpl;
import my.projects.factory.domain.service.support.FilterExecutor;
import my.projects.factory.domain.service.workflow.OperationTypeService;
import my.projects.factory.persistence.entity.workflow.OperationType;
import my.projects.factory.persistence.repository.workflow.OperationTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Implementation of {@link OperationTypeService} that handles business logic
 * for OperationType entities. Uses {@link OperationTypeMapper} to convert between
 * {@link OperationType} entities and {@link OperationTypeModel} domain models.
 */
@Service
public class OperationTypeServiceImpl
        extends PageableServiceImpl<OperationTypeModel, OperationType, UUID>
        implements OperationTypeService {

    private final OperationTypeRepository operationTypeRepository;
    private final OperationTypeMapper mapper;

    protected OperationTypeServiceImpl(OperationTypeRepository repository, OperationTypeMapper mapper) {
        super(repository, mapper::toModel, mapper::toEntity);
        this.operationTypeRepository = repository;
        this.mapper = mapper;
    }

    /**
     * Returns a paginated list of {@link OperationTypeModel} filtered by the provided criteria.
     * <p>
     * If no filter is provided, all operation types are returned.
     * If a filter is present, the method performs a case-insensitive partial match
     * (contains) on either the {@code name} or {@code code} field.
     * </p>
     *
     * @param filter   optional filter containing search criteria (nameQuery or codeQuery)
     * @param pageable pagination information (page number, size, sorting)
     * @return page of filtered and mapped {@link OperationTypeModel} instances
     */
    @Override
    public Page<OperationTypeModel> findOperationTypes(
            OperationTypeFilter filter,
            Pageable pageable
    ) {
        Map<String, String> filters = new HashMap<>();

        if (filter != null) {
            filters.put("name", filter.getNameQuery());
            filters.put("code", filter.getCodeQuery());
        }

        Map<String, Function<String, Supplier<Page<OperationType>>>> queryMap = Map.of(
                "name", value -> () -> operationTypeRepository.findByNameContainingIgnoreCase(value, pageable),
                "code", value -> () -> operationTypeRepository.findByCodeContainingIgnoreCase(value, pageable)
        );

        return FilterExecutor.execute(
                filters,
                queryMap,
                () -> operationTypeRepository.findAll(pageable),
                mapper::toModel
        );
    }
}

package my.projects.factory.domain.service.serviceImpl.workflow;

import my.projects.factory.domain.filter.workflow.ProductTypeFilter;
import my.projects.factory.domain.mapper.workflow.ProductTypeMapper;
import my.projects.factory.domain.model.workflow.ProductTypeModel;
import my.projects.factory.domain.service.serviceImpl.PageableServiceImpl;
import my.projects.factory.domain.service.support.FilterExecutor;
import my.projects.factory.domain.service.workflow.ProductTypeService;
import my.projects.factory.persistence.entity.workflow.ProductType;
import my.projects.factory.persistence.repository.workflow.ProductTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Implementation of {@link ProductTypeService} that handles business logic
 * for ProductType entities. Uses {@link ProductTypeMapper} to convert between
 * {@link ProductType} entities and {@link ProductTypeModel} domain models.
 */
@Service
public class ProductTypeServiceImpl
        extends PageableServiceImpl<ProductTypeModel, ProductType, UUID>
        implements ProductTypeService {

    private final ProductTypeRepository productTypeRepository;
    private final ProductTypeMapper mapper;

    protected ProductTypeServiceImpl(ProductTypeRepository repository, ProductTypeMapper mapper) {
        super(repository, mapper::toModel, mapper::toEntity);
        this.productTypeRepository = repository;
        this.mapper = mapper;
    }

    /**
     * Returns a paginated list of {@link ProductTypeModel} filtered by the provided criteria.
     * <p>
     * If no filter is provided, all product types are returned.
     * If a filter is present, the method performs a case-insensitive partial match
     * (contains) on either the {@code name} or {@code code} field.
     * </p>
     *
     * @param filter   optional filter containing search criteria (nameQuery or codeQuery)
     * @param pageable pagination information (page number, size, sorting)
     * @return page of filtered and mapped {@link ProductTypeModel} instances
     */
    @Override
    public Page<ProductTypeModel> findProductTypes(ProductTypeFilter filter, Pageable pageable) {
        Map<String, String> filters = new HashMap<>();

        if (filter != null) {
            filters.put("name", filter.nameQuery());
            filters.put("code", filter.codeQuery());
        }

        Map<String, Function<String, Supplier<Page<ProductType>>>> queryMap = Map.of(
                "name", value -> () -> productTypeRepository.findByNameContainingIgnoreCase(value, pageable),
                "code", value -> () -> productTypeRepository.findByCodeContainingIgnoreCase(value, pageable)
        );

        return FilterExecutor.execute(
                filters,
                queryMap,
                () -> productTypeRepository.findAll(pageable),
                mapper::toModel
        );
    }
}

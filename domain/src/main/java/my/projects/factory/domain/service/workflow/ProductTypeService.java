package my.projects.factory.domain.service.workflow;

import my.projects.factory.domain.filter.workflow.ProductTypeFilter;
import my.projects.factory.domain.model.workflow.ProductTypeModel;
import my.projects.factory.domain.service.CrudService;
import my.projects.factory.domain.service.PageableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Service interface for managing {@link ProductTypeModel} entities.
 * Provides basic CRUD operations and pageable.
 */
public interface ProductTypeService
        extends CrudService<ProductTypeModel, UUID>,
        PageableService<ProductTypeModel, UUID> {

    /**
     * Returns product types with optional filtering (used for autocomplete).
     *
     * @param filter   optional filter (name/code search)
     * @param pageable pagination configuration
     * @return page of product types
     */
    Page<ProductTypeModel> findProductTypes(ProductTypeFilter filter, Pageable pageable);
}

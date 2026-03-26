package my.projects.factory.domain.service.workflow;

import my.projects.factory.domain.filter.workflow.OperationTypeFilter;
import my.projects.factory.domain.model.workflow.OperationTypeModel;
import my.projects.factory.domain.service.CrudService;
import my.projects.factory.domain.service.PageableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Service interface for managing {@link OperationTypeModel} entities.
 * Provides basic CRUD operations and pageable.
 */
public interface OperationTypeService
        extends CrudService<OperationTypeModel, UUID>,
        PageableService<OperationTypeModel, UUID> {

    /**
     * Returns operation types with optional filtering (used for autocomplete).
     *
     * @param filter   optional filter (name/code search)
     * @param pageable pagination configuration
     * @return page of operation types
     */
    Page<OperationTypeModel> findOperationTypes(OperationTypeFilter filter, Pageable pageable);
}

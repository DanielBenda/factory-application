package my.projects.factory.domain.service.workflow;

import my.projects.factory.domain.filter.workflow.PartFilter;
import my.projects.factory.domain.model.workflow.PartModel;
import my.projects.factory.domain.service.CrudService;
import my.projects.factory.domain.service.PageableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Service interface for managing {@link PartModel} entities.
 * Provides basic CRUD operations and pageable.
 */
public interface PartService
        extends CrudService<PartModel, UUID>,
        PageableService<PartModel, UUID> {

    /**
     * Returns parts with optional filtering (used for autocomplete).
     *
     * @param filter   optional filter (name/code search)
     * @param pageable pagination configuration
     * @return page of parts
     */
    Page<PartModel> findParts(PartFilter filter, Pageable pageable);

}

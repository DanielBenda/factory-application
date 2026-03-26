package my.projects.factory.domain.service.foundation;

import my.projects.factory.domain.filter.foundation.WorkerFilter;
import my.projects.factory.domain.model.foundation.WorkerModel;
import my.projects.factory.domain.service.CrudService;
import my.projects.factory.domain.service.PageableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Service interface for managing {@link WorkerModel} entities.
 * Provides basic CRUD operations, pageable and a custom method to find a worker by surname.
 */
public interface WorkerService
        extends CrudService<WorkerModel, UUID>,
        PageableService<WorkerModel, UUID> {

    /**
     * Returns workers with optional filtering (used for autocomplete).
     *
     * @param filter   optional filter (name/surname search)
     * @param pageable pagination configuration
     * @return page of workers
     */
    Page<WorkerModel> findWorkers(WorkerFilter filter, Pageable pageable);
}

package my.projects.factory.domain.service.foundation;

import my.projects.factory.domain.filter.foundation.MachineFilter;
import my.projects.factory.domain.model.foundation.MachineModel;
import my.projects.factory.domain.service.CrudService;
import my.projects.factory.domain.service.PageableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Service interface for managing {@link MachineModel} entities.
 * Provides basic CRUD operations and pageable.
 */
public interface MachineService
        extends CrudService<MachineModel, UUID>,
        PageableService<MachineModel, UUID> {

    /**
     * Returns operation types with optional filtering (used for autocomplete).
     *
     * @param filter   optional filter (name/code search)
     * @param pageable pagination configuration
     * @return page of machines
     */
    Page<MachineModel> findMachines(MachineFilter filter, Pageable pageable);
}

package my.projects.factory.domain.service.foundation;

import my.projects.factory.domain.filter.foundation.MachineTypeFilter;
import my.projects.factory.domain.model.foundation.MachineTypeModel;
import my.projects.factory.domain.service.CrudService;
import my.projects.factory.domain.service.PageableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Service interface for managing {@link MachineTypeModel} entities.
 * Provides basic CRUD operations and pageable.
 */
public interface MachineTypeService
        extends CrudService<MachineTypeModel, UUID>,
        PageableService<MachineTypeModel, UUID> {

    /**
     * Returns machine types with optional filtering (used for autocomplete).
     *
     * @param filter   optional filter (name/code search)
     * @param pageable pagination configuration
     * @return page of machine types
     */
    Page<MachineTypeModel> findMachineTypes(MachineTypeFilter filter, Pageable pageable);
}

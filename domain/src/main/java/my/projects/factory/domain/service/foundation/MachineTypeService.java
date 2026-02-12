package my.projects.factory.domain.service.foundation;

import my.projects.factory.domain.model.foundation.MachineTypeModel;
import my.projects.factory.domain.service.CrudService;
import my.projects.factory.domain.service.PageableService;

import java.util.UUID;

/**
 * Service interface for managing {@link MachineTypeModel} entities.
 * Provides basic CRUD operations and pageable.
 */
public interface MachineTypeService
        extends CrudService<MachineTypeModel, UUID>,
        PageableService<MachineTypeModel, UUID> {
}

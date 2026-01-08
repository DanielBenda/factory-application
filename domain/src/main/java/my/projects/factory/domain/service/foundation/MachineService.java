package my.projects.factory.domain.service.foundation;

import my.projects.factory.domain.model.foundation.MachineModel;
import my.projects.factory.domain.service.CrudService;

import java.util.UUID;

/**
 * Service interface for managing {@link MachineModel} entities.
 * Provides basic CRUD operations and a custom method to find a machine.
 */
public interface MachineService extends CrudService<MachineModel, UUID> {
}

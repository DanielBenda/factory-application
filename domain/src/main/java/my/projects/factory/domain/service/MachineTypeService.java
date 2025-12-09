package my.projects.factory.domain.service;

import my.projects.factory.domain.model.MachineTypeModel;

import java.util.UUID;

/**
 * Service interface for managing {@link MachineTypeModel} entities.
 * Provides basic CRUD operations.
 */
public interface MachineTypeService extends CrudService<MachineTypeModel, UUID> {
}

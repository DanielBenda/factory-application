package my.projects.factory.domain.service.foundation;

import my.projects.factory.domain.model.foundation.DepartmentModel;
import my.projects.factory.domain.service.CrudService;

import java.util.UUID;

/**
 * Service interface for managing {@link DepartmentModel} entities.
 * Provides basic CRUD operations.
 */
public interface DepartmentService extends CrudService<DepartmentModel, UUID> {
}

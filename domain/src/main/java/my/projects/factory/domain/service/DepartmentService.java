package my.projects.factory.domain.service;

import my.projects.factory.domain.model.DepartmentModel;

import java.util.UUID;

/**
 * Service interface for managing {@link DepartmentModel} entities.
 * Provides basic CRUD operations and a custom method to find a worker by surname.
 */
public interface DepartmentService extends CrudService<DepartmentModel, UUID> {
}

package my.projects.factory.domain.service.workflow;

import my.projects.factory.domain.model.workflow.OperationTypeModel;
import my.projects.factory.domain.service.CrudService;

import java.util.UUID;

/**
 * Service interface for managing {@link OperationTypeModel} entities.
 * Provides basic CRUD operations.
 */
public interface OperationTypeService extends CrudService<OperationTypeModel, UUID> {
}

package my.projects.factory.domain.service.workflow;

import my.projects.factory.domain.model.workflow.OperationTypeModel;
import my.projects.factory.domain.service.CrudService;
import my.projects.factory.domain.service.PageableService;

import java.util.UUID;

/**
 * Service interface for managing {@link OperationTypeModel} entities.
 * Provides basic CRUD operations and pageable.
 */
public interface OperationTypeService
        extends CrudService<OperationTypeModel, UUID>,
        PageableService<OperationTypeModel, UUID> {
}

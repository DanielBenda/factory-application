package my.projects.factory.domain.service.workflow;

import my.projects.factory.domain.model.workflow.ProductTypeModel;
import my.projects.factory.domain.service.CrudService;
import my.projects.factory.domain.service.PageableService;

import java.util.UUID;

/**
 * Service interface for managing {@link ProductTypeModel} entities.
 * Provides basic CRUD operations and pageable.
 */
public interface ProductTypeService
        extends CrudService<ProductTypeModel, UUID>,
        PageableService<ProductTypeModel, UUID> {
}

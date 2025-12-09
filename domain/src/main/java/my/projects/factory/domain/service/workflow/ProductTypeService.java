package my.projects.factory.domain.service.workflow;

import my.projects.factory.domain.model.workflow.ProductTypeModel;
import my.projects.factory.domain.service.CrudService;

import java.util.UUID;

/**
 * Service interface for managing {@link ProductTypeModel} entities.
 * Provides basic CRUD operations.
 */
public interface ProductTypeService extends CrudService<ProductTypeModel, UUID> {
}

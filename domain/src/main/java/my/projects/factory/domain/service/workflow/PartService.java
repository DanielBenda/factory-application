package my.projects.factory.domain.service.workflow;

import my.projects.factory.domain.model.workflow.PartModel;
import my.projects.factory.domain.service.CrudService;
import my.projects.factory.domain.service.PageableService;

import java.util.UUID;

/**
 * Service interface for managing {@link PartModel} entities.
 * Provides basic CRUD operations and pageable.
 */
public interface PartService
        extends CrudService<PartModel, UUID>,
        PageableService<PartModel, UUID> {
}

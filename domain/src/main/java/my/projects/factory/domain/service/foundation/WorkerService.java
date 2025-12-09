package my.projects.factory.domain.service.foundation;

import my.projects.factory.domain.model.foundation.WorkerModel;
import my.projects.factory.domain.service.CrudService;

import java.util.UUID;

/**
 * Service interface for managing {@link WorkerModel} entities.
 * Provides basic CRUD operations and a custom method to find a worker by surname.
 */
public interface WorkerService extends CrudService<WorkerModel, UUID> {

    /**
     * Finds a worker by their surname.
     *
     * @param surname the surname to search for
     * @return the worker with the given surname, or {@code null} if not found
     */
    WorkerModel findBySurname(String surname);
}

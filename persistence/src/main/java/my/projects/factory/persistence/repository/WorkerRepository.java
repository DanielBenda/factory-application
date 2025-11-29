package my.projects.factory.persistence.repository;

import my.projects.factory.persistence.entity.Worker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for accessing {@link Worker} entities in the database.
 * <p>
 * Provides basic CRUD operations through {@link CrudRepository} and
 * a custom finder method to look up workers by surname.
 */
@Repository
public interface WorkerRepository extends CrudRepository<Worker, UUID> {

    /**
     * Finds a worker by their surname.
     *
     * @param surname the surname of the worker to find; must not be null
     * @return an {@link Optional} containing the worker if found, or empty if not found
     */
    Optional<Worker> findBySurname(String surname);
}

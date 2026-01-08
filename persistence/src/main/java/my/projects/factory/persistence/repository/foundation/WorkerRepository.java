package my.projects.factory.persistence.repository.foundation;

import my.projects.factory.persistence.entity.foundation.Worker;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
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
     * Returns a worker by id with {@code departmentId} eagerly fetched.
     *
     * @param id worker UUID, not {@code null}
     * @return optional worker with initialized department
     */
    @Override
    @NonNull
    @EntityGraph(attributePaths = "departmentId")
    Optional<Worker> findById(@NonNull UUID id);

    /**
     * Returns all workers with {@code departmentId} eagerly fetched.
     *
     * @return list of workers with initialized departments
     */
    @Override
    @NonNull
    @EntityGraph(attributePaths = "departmentId")
    List<Worker> findAll();

    /**
     * Finds a worker by their surname.
     *
     * @param surname the surname of the worker to find; must not be null
     * @return an {@link Optional} containing the worker if found, or empty if not found
     */
    Optional<Worker> findBySurname(String surname);
}

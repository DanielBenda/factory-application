package my.projects.factory.persistence.repository.foundation;

import jakarta.annotation.Nonnull;
import my.projects.factory.persistence.entity.foundation.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for accessing {@link Worker} entities in the database.
 * <p>
 * Provides JPA operations through {@link JpaRepository} and
 * a custom finder method to look up workers by surname.
 */
@Repository
public interface WorkerRepository extends JpaRepository<Worker, UUID> {

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
     * @return pages of workers with initialized departments
     */
    @Override
    @NonNull
    @EntityGraph(attributePaths = "departmentId")
    Page<Worker> findAll(@Nonnull Pageable pageable);

    /**
     * Finds a worker by their surname.
     *
     * @param surname the surname of the worker to find; must not be null
     * @return an {@link Optional} containing the worker if found, or empty if not found
     */
    Optional<Worker> findBySurname(String surname);
}

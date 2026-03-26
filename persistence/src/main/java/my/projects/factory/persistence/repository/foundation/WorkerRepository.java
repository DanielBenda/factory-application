package my.projects.factory.persistence.repository.foundation;

import jakarta.annotation.Nonnull;
import my.projects.factory.persistence.entity.foundation.Worker;
import my.projects.factory.persistence.entity.workflow.OperationType;
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
     * Finds {@link OperationType} entities contains the given search string, ignoring case.
     * <p>
     *
     * @param name     the substring to search for in the name field
     * @param pageable pagination information (page number, size, sorting)
     * @return a {@link Page} of matching {@link OperationType} entities
     */
    @EntityGraph(attributePaths = "departmentId")
    Page<Worker> findByNameContainingIgnoreCase(String name, Pageable pageable);

    /**
     * Finds {@link OperationType} entities contains the given search string, ignoring case.
     * <p>
     *
     * @param surname  the substring to search for in the surname field
     * @param pageable pagination information (page number, size, sorting)
     * @return a {@link Page} of matching {@link OperationType} entities
     */
    @EntityGraph(attributePaths = "departmentId")
    Page<Worker> findBySurnameContainingIgnoreCase(String surname, Pageable pageable);

}

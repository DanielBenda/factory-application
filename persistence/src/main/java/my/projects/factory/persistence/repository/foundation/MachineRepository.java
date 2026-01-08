package my.projects.factory.persistence.repository.foundation;

import my.projects.factory.persistence.entity.foundation.Machine;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for accessing {@link Machine} entities in the database.
 * <p>
 * Provides basic CRUD operations through {@link CrudRepository}.
 */
@Repository
public interface MachineRepository extends CrudRepository<Machine, UUID> {

    /**
     * Returns a machine by id with {@code machineTypeId} eagerly fetched.
     *
     * @param id machine UUID, not {@code null}
     * @return optional machine with initialized machine type
     */
    @Override
    @NonNull
    @EntityGraph(attributePaths = "machineTypeId")
    Optional<Machine> findById(@NonNull UUID id);

    /**
     * Returns all machines with {@code machineTypeId} eagerly fetched.
     *
     * @return list of machines with initialized machine types
     */
    @Override
    @NonNull
    @EntityGraph(attributePaths = "machineTypeId")
    List<Machine> findAll();
}

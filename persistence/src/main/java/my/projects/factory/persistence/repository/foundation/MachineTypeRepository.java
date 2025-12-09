package my.projects.factory.persistence.repository.foundation;

import my.projects.factory.persistence.entity.foundation.MachineType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for accessing {@link MachineType} entities in the database.
 * <p>
 * Provides basic CRUD operations through {@link CrudRepository} and
 */
@Repository
public interface MachineTypeRepository extends CrudRepository<MachineType, UUID> {
}

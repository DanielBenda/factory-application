package my.projects.factory.persistence.repository.foundation;

import my.projects.factory.persistence.entity.foundation.MachineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for accessing {@link MachineType} entities in the database.
 * <p>
 * Provides JPA operations through {@link JpaRepository} and
 */
@Repository
public interface MachineTypeRepository extends JpaRepository<MachineType, UUID> {
}

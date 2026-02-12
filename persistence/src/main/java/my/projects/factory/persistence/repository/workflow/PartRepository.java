package my.projects.factory.persistence.repository.workflow;

import my.projects.factory.persistence.entity.workflow.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for accessing {@link Part} entities in the database.
 * <p>
 * Provides JPA operations through {@link JpaRepository} and
 */
@Repository
public interface PartRepository extends JpaRepository<Part, UUID> {
}

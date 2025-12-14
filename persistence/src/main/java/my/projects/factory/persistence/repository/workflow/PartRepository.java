package my.projects.factory.persistence.repository.workflow;

import my.projects.factory.persistence.entity.workflow.Part;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for accessing {@link Part} entities in the database.
 * <p>
 * Provides basic CRUD operations through {@link CrudRepository} and
 */
@Repository
public interface PartRepository extends CrudRepository<Part, UUID> {
}

package my.projects.factory.persistence.repository.foundation;

import my.projects.factory.persistence.entity.foundation.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for accessing {@link Department} entities in the database.
 * <p>
 * Provides JPA operations through {@link JpaRepository} and
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {
}

package my.projects.factory.persistence.repository.workflow;

import my.projects.factory.persistence.entity.workflow.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for accessing {@link ProductType} entities in the database.
 * <p>
 * Provides JPA operations through {@link JpaRepository}.
 */
@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, UUID> {
}

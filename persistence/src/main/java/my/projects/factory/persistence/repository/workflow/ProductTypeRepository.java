package my.projects.factory.persistence.repository.workflow;

import my.projects.factory.persistence.entity.workflow.ProductType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for accessing {@link ProductType} entities in the database.
 * <p>
 * Provides basic CRUD operations through {@link CrudRepository} and
 * a custom finder method to look up workers by surname.
 */
@Repository
public interface ProductTypeRepository extends CrudRepository<ProductType, UUID> {
}

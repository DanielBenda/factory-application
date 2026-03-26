package my.projects.factory.persistence.repository.workflow;

import my.projects.factory.persistence.entity.workflow.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    /**
     * Finds {@link ProductType} entities contains the given search string, ignoring case.
     * <p>
     *
     * @param name     the substring to search for in the name field
     * @param pageable pagination information (page number, size, sorting)
     * @return a {@link Page} of matching {@link ProductType} entities
     */
    Page<ProductType> findByNameContainingIgnoreCase(String name, Pageable pageable);

    /**
     * Finds {@link ProductType} entities contains the given search string, ignoring case.
     * <p>
     *
     * @param code     the substring to search for in the code field
     * @param pageable pagination information (page number, size, sorting)
     * @return a {@link Page} of matching {@link ProductType} entities
     */
    Page<ProductType> findByCodeContainingIgnoreCase(String code, Pageable pageable);

}

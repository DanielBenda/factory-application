package my.projects.factory.persistence.repository.workflow;

import my.projects.factory.persistence.entity.workflow.OperationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for accessing {@link OperationType} entities in the database.
 * <p>
 * Provides JPA operations through {@link JpaRepository} and
 */
@Repository
public interface OperationTypeRepository extends JpaRepository<OperationType, UUID> {

    /**
     * Finds {@link OperationType} entities contains the given search string, ignoring case.
     * <p>
     *
     * @param name     the substring to search for in the name field
     * @param pageable pagination information (page number, size, sorting)
     * @return a {@link Page} of matching {@link OperationType} entities
     */
    Page<OperationType> findByNameContainingIgnoreCase(String name, Pageable pageable);

    /**
     * Finds {@link OperationType} entities contains the given search string, ignoring case.
     * <p>
     *
     * @param code     the substring to search for in the code field
     * @param pageable pagination information (page number, size, sorting)
     * @return a {@link Page} of matching {@link OperationType} entities
     */
    Page<OperationType> findByCodeContainingIgnoreCase(String code, Pageable pageable);

}

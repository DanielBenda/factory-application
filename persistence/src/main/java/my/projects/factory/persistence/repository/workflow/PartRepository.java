package my.projects.factory.persistence.repository.workflow;

import my.projects.factory.persistence.entity.workflow.Part;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    /**
     * Finds {@link Part} entities contains the given search string, ignoring case.
     * <p>
     *
     * @param name     the substring to search for in the name field
     * @param pageable pagination information (page number, size, sorting)
     * @return a {@link Page} of matching {@link Part} entities
     */
    Page<Part> findByNameContainingIgnoreCase(String name, Pageable pageable);

    /**
     * Finds {@link Part} entities contains the given search string, ignoring case.
     * <p>
     *
     * @param code     the substring to search for in the code field
     * @param pageable pagination information (page number, size, sorting)
     * @return a {@link Page} of matching {@link Part} entities
     */
    Page<Part> findByCodeContainingIgnoreCase(String code, Pageable pageable);

}

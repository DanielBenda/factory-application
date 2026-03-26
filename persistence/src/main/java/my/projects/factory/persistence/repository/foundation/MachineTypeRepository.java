package my.projects.factory.persistence.repository.foundation;

import my.projects.factory.persistence.entity.foundation.MachineType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for accessing {@link MachineType} entities in the database.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD operations and pagination support.
 * This repository also defines autocomplete-style query methods for searching by name and code.
 * </p>
 */
@Repository
public interface MachineTypeRepository extends JpaRepository<MachineType, UUID> {

    /**
     * Finds {@link MachineType} entities contains the given search string, ignoring case.
     * <p>
     * @param name     the substring to search for in the name field
     * @param pageable pagination information (page number, size, sorting)
     * @return a {@link Page} of matching {@link MachineType} entities
     */
    Page<MachineType> findByNameContainingIgnoreCase(String name, Pageable pageable);

    /**
     * Finds {@link MachineType} entities contains the given search string, ignoring case.
     * <p>
     * @param code     the substring to search for in the code field
     * @param pageable pagination information (page number, size, sorting)
     * @return a {@link Page} of matching {@link MachineType} entities
     */
    Page<MachineType> findByCodeContainingIgnoreCase(String code, Pageable pageable);

}

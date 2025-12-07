package my.projects.factory.domain.mapper;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

/**
 * Utility class providing type-safe resolution of JPA entities by their UUID identifiers.
 * <p>
 * This class offers a generic method that converts a string ID into a {@link UUID} and
 * retrieves the corresponding entity from a Spring Data {@link CrudRepository}. It ensures
 * consistent error handling for invalid UUID formats and missing entities.
 */
public final class RepositoryFinder {

    /**
     * Resolves an entity by its string UUID using the provided repository.
     *
     * @param id         string representation of the entity's UUID; must not be null and must be a valid UUID
     * @param repository Spring Data repository used to load the entity; must not be null
     * @param entityName human-readable entity name used in error messages; must not be null or blank
     * @param <T>        type of the entity to be returned
     * @param <R>        specific repository type handling the entity
     * @return the resolved entity, never {@code null}
     * @throws IllegalArgumentException if the UUID format is invalid or the entity does not exist
     */
    public static <T, R extends CrudRepository<T, UUID>>
    T resolveById(String id, R repository, String entityName) {

        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    entityName + " ID has invalid UUID format: " + id, e
            );
        }

        return repository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException(
                        entityName + " not found for ID: " + id
                ));
    }
}

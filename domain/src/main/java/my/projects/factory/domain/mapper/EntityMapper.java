package my.projects.factory.domain.mapper;

/**
 * Generic mapper interface for converting between entity and domain model.
 *
 * @param <M> the domain model type
 * @param <E> the persistence entity type
 */
public interface EntityMapper<M, E> {

    /**
     * Converts a persistence entity to its corresponding domain model.
     *
     * @param entity the entity to convert
     * @return the corresponding domain model
     */
    M toModel(E entity);

    /**
     * Converts a domain model to its corresponding persistence entity.
     *
     * @param model the domain model to convert
     * @return the corresponding entity
     */
    E toEntity(M model);
}
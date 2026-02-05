package my.projects.factory.domain.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Generic service interface for paginated access to domain models.
 *
 * @param <M>  type of the domain model
 * @param <ID> type of the entity identifier
 */
public interface PageableService<M, ID> {

    /**
     * Returns a paginated list of domain models.
     *
     * @param pageable pagination information
     * @return page of domain models
     */
    Page<M> findPage(Pageable pageable);
}

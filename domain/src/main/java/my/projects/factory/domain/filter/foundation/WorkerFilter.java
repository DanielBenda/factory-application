package my.projects.factory.domain.filter.foundation;

/**
 * Filter for querying workers.
 * <p>
 * Used in pageable and autocomplete operations to search by partial
 * values of {@code name} or {@code surname}.
 * </p>
 */
public record WorkerFilter(
        String nameQuery,
        String surnameQuery
) {
}

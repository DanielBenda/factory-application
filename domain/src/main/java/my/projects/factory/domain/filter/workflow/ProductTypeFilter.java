package my.projects.factory.domain.filter.workflow;

/**
 * Filter for querying product types.
 * <p>
 * Used in pageable and autocomplete operations to search by partial
 * values of {@code name} or {@code code}.
 * </p>
 */
public record ProductTypeFilter(
        String codeQuery,
        String nameQuery
) {
}

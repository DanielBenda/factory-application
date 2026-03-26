package my.projects.factory.domain.filter.workflow;

/**
 * Filter for querying parts.
 * <p>
 * Used in pageable and autocomplete operations to search by partial
 * values of {@code name} or {@code code}.
 * </p>
 */
public record PartFilter(
        String nameQuery,
        String codeQuery
) {
}

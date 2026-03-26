package my.projects.factory.domain.filter.foundation;

/**
 * Filter for querying machine types.
 * <p>
 * Used in pageable and autocomplete operations to search by partial
 * values of {@code name} or {@code code}.
 * </p>
 */
public record MachineTypeFilter(
        String codeQuery,
        String nameQuery
) {
}
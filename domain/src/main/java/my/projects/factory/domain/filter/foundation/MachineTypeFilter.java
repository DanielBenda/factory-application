package my.projects.factory.domain.filter.foundation;

import lombok.Getter;
import lombok.Setter;

/**
 * Filter for querying machine types.
 * <p>
 * Used in pageable and autocomplete operations to search by partial
 * values of {@code name} or {@code code}.
 * </p>
 */
@Getter
@Setter
public class MachineTypeFilter {

    private String nameQuery;

    private String codeQuery;
}
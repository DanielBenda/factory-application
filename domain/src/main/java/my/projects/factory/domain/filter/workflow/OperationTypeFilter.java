package my.projects.factory.domain.filter.workflow;

import lombok.Getter;
import lombok.Setter;

/**
 * Filter for querying operation types.
 * <p>
 * Used in pageable and autocomplete operations to search by partial
 * values of {@code name} or {@code code}.
 * </p>
 */
@Getter
@Setter
public class OperationTypeFilter {

    private String nameQuery;

    private String codeQuery;
}
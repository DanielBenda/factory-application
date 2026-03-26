package my.projects.factory.domain.service.support;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Generic helper for executing filter-based queries, mainly for autocomplete.
 * <p>
 * Behavior:
 * <ul>
 *     <li>No filter → executes {@code findAll}</li>
 *     <li>One filter → executes corresponding query from {@code queryMap}</li>
 *     <li>More than one filter → throws {@link IllegalStateException}</li>
 * </ul>
 */
public class FilterExecutor {

    /**
     * Executes a filter-based query based on provided filter values and query mappings.
     *
     * @param filters   map of filter keys to filter values (e.g. "name" -> "CNC")
     * @param queryMap  map of filter keys to functions that produce repository queries
     * @param findAll   supplier returning all entities when no filters are applied
     * @param mapper    function to convert entity instances to domain models
     * @return a {@link Page} of mapped domain models based on the applied filter
     *
     * @throws IllegalArgumentException if a filter key has no corresponding query in {@code queryMap}
     * @throws IllegalStateException    if more than one filter is provided (not allowed for autocomplete)
     */
    public static <E, M> Page<M> execute(
            Map<String, String> filters,
            Map<String, Function<String, Supplier<Page<E>>>> queryMap,
            Supplier<Page<E>> findAll,
            Function<E, M> mapper
    ) {

        List<Supplier<Page<E>>> activeQueries = filters.entrySet().stream()
                .filter(e -> e.getValue() != null && !e.getValue().isBlank())
                .map(e -> {
                    Function<String, Supplier<Page<E>>> queryFunction = queryMap.get(e.getKey());
                    if (queryFunction == null) {
                        throw new IllegalArgumentException("No query defined for filter: " + e.getKey());
                    }
                    return queryFunction.apply(e.getValue());
                })
                .toList();

        if (activeQueries.size() > 1) {
            throw new IllegalStateException("Only one filter is supported for autocomplete");
        }

        if (activeQueries.isEmpty()) {
            return findAll.get().map(mapper);
        }

        return activeQueries.getFirst().get().map(mapper);
    }
}

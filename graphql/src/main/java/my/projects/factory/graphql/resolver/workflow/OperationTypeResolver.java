package my.projects.factory.graphql.resolver.workflow;

import my.projects.factory.domain.filter.workflow.OperationTypeFilter;
import my.projects.factory.domain.model.workflow.OperationTypeModel;
import my.projects.factory.domain.service.workflow.OperationTypeService;
import my.projects.factory.generated.GqlCreateOperationTypeInput;
import my.projects.factory.generated.GqlOperationType;
import my.projects.factory.generated.GqlUpdateOperationTypeInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class OperationTypeResolver {

    private final OperationTypeService operationTypeService;

    /**
     * Constructor.
     *
     * @param operationTypeService the service providing business logic for operation types
     */
    public OperationTypeResolver(OperationTypeService operationTypeService) {
        this.operationTypeService = operationTypeService;
    }

    /**
     * GraphQL query for retrieving operation types with pagination and optional filtering.
     * <p>
     *
     * @param page   zero-based index of the requested page
     * @param size   number of records per page
     * @param filter optional filter containing search parameters (e.g. nameQuery, codeQuery)
     * @return paginated list of {@link OperationTypeModel} matching the filter criteria
     */
    @QueryMapping(name = "operationTypes")
    public Page<OperationTypeModel> operationTypes(
            @Argument int page,
            @Argument int size,
            @Argument OperationTypeFilter filter
    ) {
        return operationTypeService.findOperationTypes(filter, PageRequest.of(page, size));
    }

    /**
     * Resolves the {@code items} field of {@code OperationTypePage}.
     *
     * @param page Spring Data page returned by the parent query
     * @return list of operation types for the current page
     */
    @SchemaMapping(typeName = "OperationTypePage", field = "items")
    public List<GqlOperationType> operationTypePageItems(Page<OperationTypeModel> page) {
        return page.getContent()
                .stream()
                .map(this::toGql)
                .toList();
    }

    /**
     * Resolves the {@code totalCount} field of {@code OperationTypePage}.
     *
     * @param page Spring Data page returned by the parent query
     * @return total number of operation types across all pages
     */
    @SchemaMapping(typeName = "OperationTypePage", field = "totalCount")
    public Long operationTypePageTotalCount(Page<OperationTypeModel> page) {
        return page.getTotalElements();
    }


    /**
     * Returns a single operation type by ID.
     *
     * @param id the UUID of the operation type
     * @return the operation type as a {@link GqlOperationType} object
     * @throws RuntimeException if the operation type is not found
     */
    @QueryMapping(name = "operationType")
    public GqlOperationType operationTypeById(@Argument UUID id) {
        return operationTypeService.findById(id)
                .map(this::toGql)
                .orElseThrow(() -> new RuntimeException("Operation type not found: " + id));
    }

    /**
     * Creates a new operation type.
     *
     * @param input with parameters for creating an operation type
     * @return the created operation type as {@link GqlOperationType}
     */
    @MutationMapping
    public GqlOperationType createOperationType(@Argument GqlCreateOperationTypeInput input) {
        OperationTypeModel operationTypeModel = OperationTypeModel.builder()
                .description(input.getDescription())
                .name(input.getName())
                .build();
        OperationTypeModel created = operationTypeService.create(operationTypeModel);
        return toGql(created);
    }

    /**
     * Deletes an operation type by ID.
     *
     * @param id the UUID of the operation type to delete
     * @return true if deletion was successful
     */
    @MutationMapping
    public Boolean deleteOperationType(@Argument UUID id) {
        operationTypeService.delete(id);
        return true;
    }

    /**
     * Updates an existing operationType.
     * <p>
     * Fetches the current operationType by ID, applies only non-null fields from the
     * GraphQL input (PATCH-style update), and forwards the updated domain model
     * to the service layer. Returns the updated operationType as a GraphQL type.
     *
     * @param id    the UUID of the operationType to update
     * @param input the fields to update (nullable values are ignored)
     * @return the updated operationType in GraphQL representation
     * @throws RuntimeException if the operationType with the given ID does not exist
     */
    @MutationMapping
    public GqlOperationType updateOperationType(@Argument UUID id, @Argument GqlUpdateOperationTypeInput input) {

        OperationTypeModel existing = operationTypeService.findById(id)
                .orElseThrow(() -> new RuntimeException("OperationType not found: " + id));

        OperationTypeModel updatedOperationType = OperationTypeModel.builder()
                .id(existing.id())
                .description(input.getDescription() != null ? input.getDescription() : existing.description())
                .name(input.getName() != null ? input.getName() : existing.name())
                .build();

        OperationTypeModel saved = operationTypeService.update(id, updatedOperationType);

        return toGql(saved);
    }

    /**
     * Converts a {@link OperationTypeModel} to a {@link GqlOperationType}.
     *
     * @param model the OperationTypeModel to convert
     * @return the corresponding GqlOperationType, or null if input is null
     */
    private GqlOperationType toGql(OperationTypeModel model) {
        if (model == null) {
            return null;
        }
        return GqlOperationType.builder()
                .withId(model.id())
                .withCode(model.code())
                .withDescription(model.description())
                .withName(model.name())
                .withCreated(model.created())
                .withCreatedBy(model.createdBy())
                .build();
    }
}

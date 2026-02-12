package my.projects.factory.graphql.resolver.workflow;

import my.projects.factory.domain.model.workflow.PartModel;
import my.projects.factory.domain.service.workflow.PartService;
import my.projects.factory.generated.GqlCreatePartInput;
import my.projects.factory.generated.GqlPart;
import my.projects.factory.generated.GqlUpdatePartInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

/**
 * GraphQL resolver for operations on Part entities.
 * <p>
 * Provides queries and mutations for fetching, creating, and deleting product types.
 * Converts between {@link PartModel} and {@link GqlPart} objects.
 */
@Controller
public class PartResolver {

    private final PartService partService;

    /**
     * Constructor.
     *
     * @param partService the service providing business logic for parts
     */
    public PartResolver(PartService partService) {
        this.partService = partService;
    }

    /**
     * Returns all parts.
     *
     * @return pageable of all parts as {@link GqlPart} objects
     */
    @QueryMapping(name = "parts")
    public Page<PartModel> parts(
            @Argument int page,
            @Argument int size
    ) {
        return partService.findPage(PageRequest.of(page, size));
    }

    /**
     * Resolves the {@code items} field of {@code PartPage}.
     *
     * @param page Spring Data page returned by the parent query
     * @return list of parts for the current page
     */
    @SchemaMapping(typeName = "PartPage", field = "items")
    public List<GqlPart> partPageItems(Page<PartModel> page) {
        return page.getContent()
                .stream()
                .map(this::toGql)
                .toList();
    }

    /**
     * Resolves the {@code totalCount} field of {@code PartPage}.
     *
     * @param page Spring Data page returned by the parent query
     * @return total number of parts across all pages
     */
    @SchemaMapping(typeName = "PartPage", field = "totalCount")
    public Long partPageTotalCount(Page<PartModel> page) {
        return page.getTotalElements();
    }

    /**
     * Returns a single part by ID.
     *
     * @param id the UUID of the part
     * @return the part as a {@link GqlPart} object
     * @throws RuntimeException if the part is not found
     */
    @QueryMapping(name = "part")
    public GqlPart partById(@Argument UUID id) {
        return partService.findById(id)
                .map(this::toGql)
                .orElseThrow(() -> new RuntimeException("Part not found: " + id));
    }

    /**
     * Creates a new part.
     *
     * @param input with parameters for creating a part
     * @return the created part as {@link GqlPart}
     */
    @MutationMapping
    public GqlPart createPart(@Argument GqlCreatePartInput input) {
        PartModel partModel = PartModel.builder()
                .code(input.getCode())
                .material(input.getMaterial())
                .name(input.getName())
                .build();
        PartModel created = partService.create(partModel);
        return toGql(created);
    }

    /**
     * Deletes a part by ID.
     *
     * @param id the UUID of the part to delete
     * @return true if deletion was successful
     */
    @MutationMapping
    public Boolean deletePart(@Argument UUID id) {
        partService.delete(id);
        return true;
    }

    /**
     * Updates an existing part.
     * <p>
     * Fetches the current part by ID, applies only non-null fields from the
     * GraphQL input (PATCH-style update), and forwards the updated domain model
     * to the service layer. Returns the updated part as a GraphQL type.
     *
     * @param id    the UUID of the part to update
     * @param input the fields to update (nullable values are ignored)
     * @return the updated part in GraphQL representation
     * @throws RuntimeException if the part with the given ID does not exist
     */
    @MutationMapping
    public GqlPart updatePart(@Argument UUID id, @Argument GqlUpdatePartInput input) {

        PartModel existing = partService.findById(id)
                .orElseThrow(() -> new RuntimeException("Part not found: " + id));

        PartModel updatedPart = PartModel.builder()
                .id(existing.id())
                .code(input.getCode() != null ? input.getCode() : existing.code())
                .material(input.getMaterial() != null ? input.getMaterial() : existing.material())
                .name(input.getName() != null ? input.getName() : existing.name())
                .build();

        PartModel saved = partService.update(id, updatedPart);

        return toGql(saved);
    }

    /**
     * Converts a {@link PartModel} to a {@link GqlPart}.
     *
     * @param model the PartModel to convert
     * @return the corresponding GqlPart, or null if input is null
     */
    private GqlPart toGql(PartModel model) {
        if (model == null) {
            return null;
        }
        return GqlPart.builder()
                .withId(model.id())
                .withCode(model.code())
                .withMaterial(model.material())
                .withName(model.name())
                .build();
    }
}

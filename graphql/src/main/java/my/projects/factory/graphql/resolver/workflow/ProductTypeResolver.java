package my.projects.factory.graphql.resolver.workflow;

import my.projects.factory.domain.model.workflow.ProductTypeModel;
import my.projects.factory.domain.service.workflow.ProductTypeService;
import my.projects.factory.generated.GqlCreateProductTypeInput;
import my.projects.factory.generated.GqlProductType;
import my.projects.factory.generated.GqlUpdateProductTypeInput;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

/**
 * GraphQL resolver for operations on ProductType entities.
 * <p>
 * Provides queries and mutations for fetching, creating, and deleting product types.
 * Converts between {@link ProductTypeModel} and {@link GqlProductType} objects.
 */
@Controller
public class ProductTypeResolver {

    private final ProductTypeService productTypeService;

    /**
     * Constructor.
     *
     * @param productTypeService the service providing business logic for product types
     */
    public ProductTypeResolver(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    /**
     * Returns all product types.
     *
     * @return list of all product types as {@link GqlProductType} objects
     */
    @QueryMapping(name = "productTypes")
    public List<GqlProductType> productTypes() {
        return productTypeService.findAll()
                .stream()
                .map(this::toGql)
                .toList();
    }

    /**
     * Returns a single product type by ID.
     *
     * @param id the UUID of the worker
     * @return the worker as a {@link GqlProductType} object
     * @throws RuntimeException if the worker is not found
     */
    @QueryMapping(name = "productType")
    public GqlProductType productTypeById(@Argument UUID id) {
        return productTypeService.findById(id)
                .map(this::toGql)
                .orElseThrow(() -> new RuntimeException("Product type not found: " + id));
    }

    /**
     * Creates a new product type.
     *
     * @param input with parameters for creating a product type
     * @return the created product type as {@link GqlProductType}
     */
    @MutationMapping
    public GqlProductType createProductType(@Argument GqlCreateProductTypeInput input) {
        ProductTypeModel worker = ProductTypeModel.builder()
                .code(input.getCode())
                .description(input.getDescription())
                .name(input.getName())
                .build();
        ProductTypeModel created = productTypeService.create(worker);
        return toGql(created);
    }

    /**
     * Deletes a product type by ID.
     *
     * @param id the UUID of the product type to delete
     * @return true if deletion was successful
     */
    @MutationMapping
    public Boolean deleteProductType(@Argument UUID id) {
        productTypeService.delete(id);
        return true;
    }

    /**
     * Updates an existing product type.
     * <p>
     * Fetches the current product type by ID, applies only non-null fields from the
     * GraphQL input (PATCH-style update), and forwards the updated domain model
     * to the service layer. Returns the updated product type as a GraphQL type.
     *
     * @param id    the UUID of the product type to update
     * @param input the fields to update (nullable values are ignored)
     * @return the updated product type in GraphQL representation
     * @throws RuntimeException if the product type with the given ID does not exist
     */
    @MutationMapping
    public GqlProductType updateProductType(@Argument UUID id, @Argument GqlUpdateProductTypeInput input) {

        ProductTypeModel existing = productTypeService.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductType not found: " + id));

        ProductTypeModel updatedProductType = ProductTypeModel.builder()
                .id(existing.id())
                .code(input.getCode() != null ? input.getCode() : existing.code())
                .description(input.getDescription() != null ? input.getDescription() : existing.description())
                .name(input.getName() != null ? input.getName() : existing.name())
                .build();

        ProductTypeModel saved = productTypeService.update(id, updatedProductType);

        return toGql(saved);
    }

    /**
     * Converts a {@link ProductTypeModel} to a {@link GqlProductType}.
     *
     * @param model the ProductTypeModel to convert
     * @return the corresponding GqlProductType, or null if input is null
     */
    private GqlProductType toGql(ProductTypeModel model) {
        if (model == null) {
            return null;
        }
        return GqlProductType.builder()
                .withId(model.id())
                .withCode(model.code())
                .withCreated(model.created())
                .withCreatedBy(model.createdBy())
                .withDescription(model.description())
                .withName(model.name())
                .build();
    }
}

package my.projects.factory.graphql.resolver;

import my.projects.factory.domain.model.MachineTypeModel;
import my.projects.factory.domain.service.MachineTypeService;
import my.projects.factory.generated.GqlCreateMachineTypeInput;
import my.projects.factory.generated.GqlMachineType;
import my.projects.factory.generated.GqlUpdateMachineTypeInput;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

/**
 * GraphQL resolver for operations on MachineType entities.
 * <p>
 * Provides queries and mutations for fetching, creating, and deleting machine types.
 * Converts between {@link MachineTypeModel} and {@link GqlMachineType} objects.
 */
@Controller
public class MachineTypeResolver {

    private final MachineTypeService machineTypeService;

    /**
     * Constructor.
     *
     * @param machineTypeService the service providing business logic for machine types
     */
    public MachineTypeResolver(MachineTypeService machineTypeService) {
        this.machineTypeService = machineTypeService;
    }

    /**
     * Returns all machine types.
     *
     * @return list of all machine types as {@link GqlMachineType} objects
     */
    @QueryMapping(name = "machineTypes")
    public List<GqlMachineType> machineTypes() {
        return machineTypeService.findAll()
                .stream()
                .map(this::toGql)
                .toList();
    }

    /**
     * Returns a single machine type by ID.
     *
     * @param id the UUID of the machine type
     * @return the machine type as a {@link GqlMachineType} object
     * @throws RuntimeException if the machine type is not found
     */
    @QueryMapping(name = "machineType")
    public GqlMachineType machineTypeById(@Argument UUID id) {
        return machineTypeService.findById(id)
                .map(this::toGql)
                .orElseThrow(() -> new RuntimeException("MachineType not found" + id));
    }

    /**
     * Creates a new machineType.
     *
     * @param input with parameters for creating a machine type
     * @return the created machine type as {@link GqlMachineType}
     */
    @MutationMapping
    public GqlMachineType createMachineType(@Argument GqlCreateMachineTypeInput input) {
        MachineTypeModel machineType = MachineTypeModel.builder()
                .id(UUID.randomUUID())
                .code(input.getCode())
                .description(input.getDescription())
                .name(input.getName())
                .build();
        MachineTypeModel created = machineTypeService.create(machineType);
        return toGql(created);
    }

    /**
     * Deletes a machine type by ID.
     *
     * @param id the UUID of the machine type to delete
     * @return true if deletion was successful
     */
    @MutationMapping
    public Boolean deleteMachineType(@Argument UUID id) {
        machineTypeService.delete(id);
        return true;
    }

    /**
     * Updates an existing machine type.
     * <p>
     * Fetches the current machine type by ID, applies only non-null fields from the
     * GraphQL input (PATCH-style update), and forwards the updated domain model
     * to the service layer. Returns the updated machine type as a GraphQL type.
     *
     * @param id    the UUID of the machine type to update
     * @param input the fields to update (nullable values are ignored)
     * @return the updated machine type in GraphQL representation
     * @throws RuntimeException if the machine type with the given ID does not exist
     */
    @MutationMapping
    public GqlMachineType updateMachineType(@Argument UUID id, @Argument GqlUpdateMachineTypeInput input) {

        MachineTypeModel existing = machineTypeService.findById(id)
                .orElseThrow(() -> new RuntimeException("MachineType not found: " + id));

        MachineTypeModel updatedMachineType = MachineTypeModel.builder()
                .id(existing.id())
                .code(input.getCode() != null ? input.getCode() : existing.code())
                .description(input.getDescription() != null ? input.getDescription() : existing.description())
                .name(input.getName() != null ? input.getName() : existing.name())
                .build();
        MachineTypeModel saved = machineTypeService.update(id, updatedMachineType);

        return toGql(saved);
    }

    /**
     * Converts a {@link MachineTypeModel} to a {@link GqlMachineType}.
     *
     * @param machineTypeModel the MachineTypeModel to convert
     * @return the corresponding GqlMachineType, or null if input is null
     */
    private GqlMachineType toGql(MachineTypeModel machineTypeModel) {
        if (machineTypeModel == null) {
            return null;
        }
        return GqlMachineType.builder()
                .withId(machineTypeModel.id())
                .withCode(machineTypeModel.code())
                .withDescription(machineTypeModel.description())
                .withName(machineTypeModel.name())
                .build();
    }
}

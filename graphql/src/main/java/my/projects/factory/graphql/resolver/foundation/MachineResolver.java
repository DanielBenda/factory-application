package my.projects.factory.graphql.resolver.foundation;

import my.projects.factory.domain.model.foundation.MachineModel;
import my.projects.factory.domain.service.foundation.MachineService;
import my.projects.factory.generated.GqlCreateMachineInput;
import my.projects.factory.generated.GqlMachine;
import my.projects.factory.generated.GqlUpdateMachineInput;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

/**
 * GraphQL resolver for operations on Machine entities.
 * <p>
 * Provides queries and mutations for fetching, creating, and deleting workers.
 * Converts between {@link MachineModel} and {@link GqlMachine} objects.
 */
@Controller
public class MachineResolver {

    private final MachineService machineService;

    /**
     * Constructor.
     *
     * @param machineService the service providing business logic for machines
     */
    public MachineResolver(MachineService machineService) {
        this.machineService = machineService;
    }

    /**
     * Returns all machines.
     *
     * @return list of all machines as {@link GqlMachine} objects
     */
    @QueryMapping(name = "machines")
    public List<GqlMachine> machines() {
        return machineService.findAll()
                .stream()
                .map(this::toGql)
                .toList();
    }

    /**
     * Returns a single machine by ID.
     *
     * @param id the UUID of the machine
     * @return the machine as a {@link GqlMachine} object
     * @throws RuntimeException if the machine is not found
     */
    @QueryMapping(name = "machine")
    public GqlMachine machineById(@Argument UUID id) {
        return machineService.findById(id)
                .map(this::toGql)
                .orElseThrow(() -> new RuntimeException("Machine not found: " + id));
    }

    /**
     * Creates a new machine.
     *
     * @param input with parameters for creating a machine
     * @return the created machine as {@link GqlMachine}
     */
    @MutationMapping
    public GqlMachine createMachine(@Argument GqlCreateMachineInput input) {
        MachineModel machine = MachineModel.builder()
                .code(input.getCode())
                .machineType(input.getMachineType())
                .name(input.getName())
                .year(input.getYear())
                .build();
        MachineModel created = machineService.create(machine);
        return toGql(created);
    }

    /**
     * Deletes a machine by ID.
     *
     * @param id the UUID of the machine to delete
     * @return true if deletion was successful
     */
    @MutationMapping
    public Boolean deleteMachine(@Argument UUID id) {
        machineService.delete(id);
        return true;
    }

    /**
     * Updates an existing machine.
     * <p>
     * Fetches the current machine by ID, applies only non-null fields from the
     * GraphQL input (PATCH-style update), and forwards the updated domain model
     * to the service layer. Returns the updated machine as a GraphQL type.
     *
     * @param id    the UUID of the machine to update
     * @param input the fields to update (nullable values are ignored)
     * @return the updated machine in GraphQL representation
     * @throws RuntimeException if the machine with the given ID does not exist
     */
    @MutationMapping
    public GqlMachine updateMachine(@Argument UUID id, @Argument GqlUpdateMachineInput input) {

        MachineModel existing = machineService.findById(id)
                .orElseThrow(() -> new RuntimeException("Machine not found: " + id));

        MachineModel updatedMachine = MachineModel.builder()
                .id(existing.id())
                .code(input.getCode() != null ? input.getCode() : existing.code())
                .machineType(input.getMachineType() != null ? input.getMachineType() : existing.machineType())
                .name(input.getName() != null ? input.getName() : existing.name())
                .year(input.getYear() != null ? input.getYear() : existing.year())
                .build();

        MachineModel saved = machineService.update(id, updatedMachine);

        return toGql(saved);
    }

    /**
     * Converts a {@link MachineModel} to a {@link GqlMachine}.
     *
     * @param model the MachineModel to convert
     * @return the corresponding GqlMachine, or null if input is null
     */
    private GqlMachine toGql(MachineModel model) {
        if (model == null) {
            return null;
        }
        return GqlMachine.builder()
                .withId(model.id())
                .withCode(model.code())
                .withMachineType(model.machineType())
                .withName(model.name())
                .withYear(model.year())
                .build();
    }
}

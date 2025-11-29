package my.projects.factory.graphql.resolver;

import my.projects.factory.domain.model.WorkerModel;
import my.projects.factory.generated.GqlWorker;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import my.projects.factory.domain.service.WorkerService;

import java.util.List;
import java.util.UUID;

/**
 * GraphQL resolver for operations on Worker entities.
 * <p>
 * Provides queries and mutations for fetching, creating, and deleting workers.
 * Converts between {@link WorkerModel} and {@link GqlWorker} objects.
 */
@Controller
public class WorkerResolver {

    private final WorkerService workerService;

    /**
     * Constructor.
     *
     * @param workerService the service providing business logic for workers
     */
    public WorkerResolver(WorkerService workerService) {
        this.workerService = workerService;
    }

    /**
     * Returns all workers.
     *
     * @return list of all workers as {@link GqlWorker} objects
     */
    @QueryMapping(name = "workers")
    public List<GqlWorker> workers() {
        return workerService.findAll()
                .stream()
                .map(this::toGql)
                .toList();
    }

    /**
     * Returns a single worker by ID.
     *
     * @param id the UUID of the worker
     * @return the worker as a {@link GqlWorker} object
     * @throws RuntimeException if the worker is not found
     */
    @QueryMapping
    public GqlWorker workerById(@Argument UUID id) {
        return workerService.findById(id)
                .map(this::toGql)
                .orElseThrow(() -> new RuntimeException("Worker not found: " + id));
    }

    /**
     * Creates a new worker.
     *
     * @param name       the first name of the worker
     * @param surname    the surname of the worker
     * @param department the department name
     * @param position   the worker's position
     * @return the created worker as {@link GqlWorker}
     */
    @MutationMapping
    public GqlWorker createWorker(@Argument String name,
                                  @Argument String surname,
                                  @Argument String department,
                                  @Argument String position) {
        WorkerModel worker = WorkerModel.builder()
                .id(UUID.randomUUID())
                .name(name)
                .surname(surname)
                .department(department)
                .workPosition(position)
                .build();
        WorkerModel created = workerService.create(worker);
        return toGql(created);
    }

    /**
     * Deletes a worker by ID.
     *
     * @param id the UUID of the worker to delete
     * @return true if deletion was successful
     */
    @MutationMapping
    public Boolean deleteWorker(@Argument UUID id) {
        workerService.delete(id);
        return true;
    }

    /**
     * Converts a {@link WorkerModel} to a {@link GqlWorker}.
     *
     * @param model the WorkerModel to convert
     * @return the corresponding GqlWorker, or null if input is null
     */
    private GqlWorker toGql(WorkerModel model) {
        if (model == null) {
            return null;
        }
        return GqlWorker.builder()
                .withId(model.id())
                .withName(model.name())
                .withSurname(model.surname())
                .withDepartment(model.department())
                .withWorkPosition(model.workPosition())
                .build();
    }

    /**
     * Converts a {@link GqlWorker} to a {@link WorkerModel}.
     *
     * @param gql the GqlWorker to convert
     * @return the corresponding WorkerModel, or null if input is null
     */
    private WorkerModel fromGql(GqlWorker gql) {
        if (gql == null) {
            return null;
        }
        return WorkerModel.builder()
                .id(gql.getId())
                .name(gql.getName())
                .surname(gql.getSurname())
                .department(gql.getDepartment())
                .workPosition(gql.getWorkPosition())
                .build();
    }
}
package my.projects.factory.graphql.resolver.foundation;

import my.projects.factory.domain.filter.foundation.WorkerFilter;
import my.projects.factory.domain.model.foundation.WorkerModel;
import my.projects.factory.domain.service.foundation.WorkerService;
import my.projects.factory.generated.GqlCreateWorkerInput;
import my.projects.factory.generated.GqlUpdateWorkerInput;
import my.projects.factory.generated.GqlWorker;
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
     * GraphQL query for retrieving workers with pagination and optional filtering.
     * <p>
     *
     * @param page   zero-based index of the requested page
     * @param size   number of records per page
     * @param filter optional filter containing search parameters (e.g. nameQuery, surnameQuery)
     * @return paginated list of {@link WorkerModel} matching the filter criteria
     */
    @QueryMapping(name = "workers")
    public Page<WorkerModel> workers(
            @Argument int page,
            @Argument int size,
            @Argument WorkerFilter filter
    ) {
        return workerService.findWorkers(filter, PageRequest.of(page, size));
    }

    /**
     * Resolves the {@code items} field of {@code WorkerPage}.
     *
     * @param page Spring Data page returned by the parent query
     * @return list of workers for the current page
     */
    @SchemaMapping(typeName = "WorkerPage", field = "items")
    public List<GqlWorker> workerPageItems(Page<WorkerModel> page) {
        return page.getContent()
                .stream()
                .map(this::toGql)
                .toList();
    }

    /**
     * Resolves the {@code totalCount} field of {@code WorkerPage}.
     *
     * @param page Spring Data page returned by the parent query
     * @return total number of workers across all pages
     */
    @SchemaMapping(typeName = "WorkerPage", field = "totalCount")
    public Long workerTotalCount(Page<WorkerModel> page) {
        return page.getTotalElements();
    }

    /**
     * Returns a single worker by ID.
     *
     * @param id the UUID of the worker
     * @return the worker as a {@link GqlWorker} object
     * @throws RuntimeException if the worker is not found
     */
    @QueryMapping(name = "worker")
    public GqlWorker workerById(@Argument UUID id) {
        return workerService.findById(id)
                .map(this::toGql)
                .orElseThrow(() -> new RuntimeException("Worker not found: " + id));
    }

    /**
     * Creates a new worker.
     *
     * @param input with parameters for creating a worker
     * @return the created worker as {@link GqlWorker}
     */
    @MutationMapping
    public GqlWorker createWorker(@Argument GqlCreateWorkerInput input) {
        WorkerModel worker = WorkerModel.builder()
                .department(input.getDepartment())
                .name(input.getName())
                .surname(input.getSurname())
                .systemRole(input.getSystemRole())
                .workPosition(input.getWorkPosition())
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
     * Updates an existing worker.
     * <p>
     * Fetches the current worker by ID, applies only non-null fields from the
     * GraphQL input (PATCH-style update), and forwards the updated domain model
     * to the service layer. Returns the updated worker as a GraphQL type.
     *
     * @param id    the UUID of the worker to update
     * @param input the fields to update (nullable values are ignored)
     * @return the updated worker in GraphQL representation
     * @throws RuntimeException if the worker with the given ID does not exist
     */
    @MutationMapping
    public GqlWorker updateWorker(@Argument UUID id, @Argument GqlUpdateWorkerInput input) {

        WorkerModel existing = workerService.findById(id)
                .orElseThrow(() -> new RuntimeException("Worker not found: " + id));

        WorkerModel updatedWorker = WorkerModel.builder()
                .id(existing.id())
                .department(input.getDepartment() != null ? input.getDepartment() : existing.department())
                .name(input.getName() != null ? input.getName() : existing.name())
                .surname(input.getSurname() != null ? input.getSurname() : existing.surname())
                .systemRole(input.getSystemRole() != null ? input.getSystemRole() : existing.systemRole())
                .workPosition(input.getWorkPosition() != null ? input.getWorkPosition() : existing.workPosition())
                .build();

        WorkerModel saved = workerService.update(id, updatedWorker);

        return toGql(saved);
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
                .withCreated(model.created())
                .withCreatedBy(model.createdBy())
                .withDepartment(model.department())
                .withName(model.name())
                .withSurname(model.surname())
                .withSystemRole(model.systemRole())
                .withWorkPosition(model.workPosition())
                .build();
    }
}
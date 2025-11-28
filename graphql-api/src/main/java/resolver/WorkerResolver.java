package resolver;

import model.WorkerModel;
import my.projects.factory.generated.GqlWorker;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import service.WorkerService;

import java.util.List;
import java.util.UUID;

@Controller
public class WorkerResolver {

    private final WorkerService workerService;

    public WorkerResolver(WorkerService workerService) {
        this.workerService = workerService;
    }

    @QueryMapping(name = "workers")
    public List<GqlWorker> workers() {
        return workerService.findAll()
                .stream()
                .map(this::toGql)
                .toList();
    }

    @QueryMapping
    public GqlWorker workerById(@Argument UUID id) {
        return workerService.findById(id)
                .map(this::toGql)
                .orElse(null);
    }

    @MutationMapping
    public GqlWorker createWorker(@Argument String name,
                                  @Argument String surname,
                                  @Argument String position) {
        // TODO: add departmentId handling
        WorkerModel worker = WorkerModel.builder()
                .id(UUID.randomUUID())
                .name(name)
                .surname(surname)
                .departmentId(UUID.randomUUID())
                .workPosition(position)
                .build();
        WorkerModel created = workerService.create(worker);
        return toGql(created);
    }

    @MutationMapping
    public Boolean deleteWorker(@Argument UUID id) {
        workerService.delete(id);
        return true;
    }

    private GqlWorker toGql(WorkerModel model) {
        if (model == null) {
            return null;
        }
        return GqlWorker.builder()
                .withId(model.id())
                .withName(model.name())
                .withSurname(model.surname())
                .withDepartmentId(model.departmentId())
                .withWorkPosition(model.workPosition())
                .build();
    }

    private WorkerModel fromGql(GqlWorker gql) {
        if (gql == null) {
            return null;
        }
        return WorkerModel.builder()
                .id(gql.getId())
                .name(gql.getName())
                .surname(gql.getSurname())
                .departmentId(gql.getDepartmentId())
                .workPosition(gql.getWorkPosition())
                .build();
    }
}

package resolver;

import model.Worker;
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

    @QueryMapping
    public List<Worker> workers() {
        return workerService.findAll();
    }

    @QueryMapping
    public Worker workerById(@Argument UUID id) {
        return workerService.findById(id).orElse(null);
    }

    @MutationMapping
    public Worker createWorker(@Argument String name,
                                         @Argument String surname,
                                         @Argument String position) {
        // TODO add department id
        Worker worker = Worker.builder()
                .id(UUID.randomUUID())
                .name(name)
                .surname(surname)
                .workPosition(position)
                .build();
        return workerService.create(worker);
    }

    @MutationMapping
    public Boolean deleteWorker(@Argument UUID id) {
        workerService.delete(id);
        return true;
    }
}


package my.projects.factory.domain.mapper;

import my.projects.factory.persistence.entity.Worker;
import my.projects.factory.domain.model.WorkerModel;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between {@link Worker} entity and {@link WorkerModel} domain model.
 * <p>
 * Provides methods to transform data from database entities to domain models and vice versa.
 */
@Component
public class WorkerMapper implements EntityMapper<WorkerModel, Worker> {

    /**
     * Converts a {@link Worker} entity to a {@link WorkerModel}.
     *
     * @param worker the {@code Worker} entity to convert; must not be null
     * @return the corresponding {@code WorkerModel} with id, name, department, surname and workPosition set
     */
    @Override
    public WorkerModel toModel(Worker worker) {
        return WorkerModel.builder()
                .id(worker.getId())
                .name(worker.getName())
                .department(worker.getDepartmentId().getName())
                .surname(worker.getSurname())
                .workPosition(worker.getWorkPosition())
                .build();
    }

    /**
     * Converts a {@link WorkerModel} to a {@link Worker} entity.
     *
     * @param workerModel the {@code WorkerModel} to convert; must not be null
     * @return a {@code Worker} entity with id, name, surname and workPosition set
     */
    @Override
    public Worker toEntity(WorkerModel workerModel) {
        Worker worker = new Worker();
        worker.setId(workerModel.id());
        worker.setName(workerModel.name());
        worker.setSurname(workerModel.surname());
        worker.setWorkPosition(workerModel.workPosition());
        return worker;
    }
}

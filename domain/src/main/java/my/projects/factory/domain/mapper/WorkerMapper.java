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
     * @param entity the {@code Worker} entity to convert; must not be null
     * @return the corresponding {@code WorkerModel} with id, name, surname and workPosition set
     */
    @Override
    public WorkerModel toModel(Worker entity) {
        return WorkerModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .workPosition(entity.getWorkPosition())
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

package mapper;

import entity.Worker;
import model.WorkerModel;

public class WorkerMapper implements EntityMapper<WorkerModel, Worker> {

    @Override
    public WorkerModel toModel(Worker entity) {
        return WorkerModel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .workPosition(entity.getWorkPosition())
                .build();
    }

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

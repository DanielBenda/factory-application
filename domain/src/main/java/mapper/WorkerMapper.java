package mapper;

import entity.WorkerEntity;
import model.Worker;

public class WorkerMapper implements EntityMapper<Worker, WorkerEntity> {

    @Override
    public Worker toModel(WorkerEntity entity) {
        return Worker.builder()
                .id(entity.getId())
                .name(entity.getName())
                .surname(entity.getSurname())
                .workPosition(entity.getWorkPosition())
                .build();
    }

    @Override
    public WorkerEntity toEntity(Worker model) {
        WorkerEntity entity = new WorkerEntity();
        entity.setId(model.id());
        entity.setName(model.name());
        entity.setSurname(model.surname());
        entity.setWorkPosition(model.workPosition());
        return entity;
    }
}

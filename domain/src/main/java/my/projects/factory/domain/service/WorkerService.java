package my.projects.factory.domain.service;

import my.projects.factory.domain.model.WorkerModel;

import java.util.UUID;

public interface WorkerService extends CrudService<WorkerModel, UUID> {

    WorkerModel findBySurname(String surname);
}

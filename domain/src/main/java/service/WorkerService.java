package service;

import model.WorkerModel;

import java.util.UUID;

public interface WorkerService extends CrudService<WorkerModel, UUID> {

    WorkerModel findBySurname(String surname);
}

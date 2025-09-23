package service;

import model.Worker;

import java.util.UUID;

public interface WorkerService extends CrudService<Worker, UUID> {

    Worker findBySurname(String surname);
}

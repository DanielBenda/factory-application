package service.serviceImpl;

import entity.Worker;
import mapper.WorkerMapper;
import model.WorkerModel;
import org.springframework.stereotype.Service;
import repository.WorkerRepository;
import service.WorkerService;

import java.util.UUID;

@Service
public class WorkerServiceImpl
        extends CrudServiceImpl<WorkerModel, Worker, UUID>
        implements WorkerService {

    private final WorkerRepository repository;
    private final WorkerMapper mapper;

    public WorkerServiceImpl(WorkerRepository repository, WorkerMapper mapper) {
        super(repository, mapper::toModel, mapper::toEntity);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public WorkerModel findBySurname(String surname) {
        return repository.findBySurname(surname)
                .map(mapper::toModel)
                .orElse(null);
    }
}




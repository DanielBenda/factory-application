package my.projects.factory.domain.service.serviceImpl;

import my.projects.factory.persistence.entity.Worker;
import my.projects.factory.domain.mapper.WorkerMapper;
import my.projects.factory.domain.model.WorkerModel;
import org.springframework.stereotype.Service;
import my.projects.factory.persistence.repository.WorkerRepository;
import my.projects.factory.domain.service.WorkerService;

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




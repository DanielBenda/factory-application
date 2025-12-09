package my.projects.factory.domain.service.serviceImpl.foundation;

import my.projects.factory.domain.service.serviceImpl.CrudServiceImpl;
import my.projects.factory.persistence.entity.Worker;
import my.projects.factory.domain.mapper.foundation.WorkerMapper;
import my.projects.factory.domain.model.foundation.WorkerModel;
import org.springframework.stereotype.Service;
import my.projects.factory.persistence.repository.WorkerRepository;
import my.projects.factory.domain.service.foundation.WorkerService;

import java.util.UUID;

/**
 * Implementation of {@link WorkerService} that handles business logic
 * for Worker entities. Uses {@link WorkerMapper} to convert between
 * {@link Worker} entities and {@link WorkerModel} domain models.
 */
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
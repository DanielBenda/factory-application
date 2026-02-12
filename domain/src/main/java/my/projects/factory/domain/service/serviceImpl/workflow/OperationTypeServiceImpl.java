package my.projects.factory.domain.service.serviceImpl.workflow;

import my.projects.factory.domain.mapper.workflow.OperationTypeMapper;
import my.projects.factory.domain.model.workflow.OperationTypeModel;
import my.projects.factory.domain.service.serviceImpl.PageableServiceImpl;
import my.projects.factory.domain.service.workflow.OperationTypeService;
import my.projects.factory.persistence.entity.workflow.OperationType;
import my.projects.factory.persistence.repository.workflow.OperationTypeRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementation of {@link OperationTypeService} that handles business logic
 * for OperationType entities. Uses {@link OperationTypeMapper} to convert between
 * {@link OperationType} entities and {@link OperationTypeModel} domain models.
 */
@Service
public class OperationTypeServiceImpl
        extends PageableServiceImpl<OperationTypeModel, OperationType, UUID>
        implements OperationTypeService {

    protected OperationTypeServiceImpl(OperationTypeRepository repository, OperationTypeMapper mapper) {
        super(repository, mapper::toModel, mapper::toEntity);
    }
}

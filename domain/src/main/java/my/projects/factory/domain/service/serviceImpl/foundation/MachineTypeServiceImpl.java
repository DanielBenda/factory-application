package my.projects.factory.domain.service.serviceImpl.foundation;

import my.projects.factory.domain.mapper.foundation.MachineTypeMapper;
import my.projects.factory.domain.model.foundation.MachineTypeModel;
import my.projects.factory.domain.service.foundation.MachineTypeService;
import my.projects.factory.domain.service.serviceImpl.PageableServiceImpl;
import my.projects.factory.persistence.entity.foundation.MachineType;
import my.projects.factory.persistence.repository.foundation.MachineTypeRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementation of {@link MachineTypeService} that handles business logic
 * for MachineType entities. Uses {@link MachineTypeMapper} to convert between
 * {@link MachineType} entities and {@link MachineTypeModel} domain models.
 */
@Service
public class MachineTypeServiceImpl
        extends PageableServiceImpl<MachineTypeModel, MachineType, UUID>
        implements MachineTypeService {

    public MachineTypeServiceImpl(MachineTypeRepository repository, MachineTypeMapper mapper) {
        super(repository, mapper::toModel, mapper::toEntity);
    }
}

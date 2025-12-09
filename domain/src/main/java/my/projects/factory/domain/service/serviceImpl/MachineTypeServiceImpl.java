package my.projects.factory.domain.service.serviceImpl;

import my.projects.factory.domain.mapper.MachineTypeMapper;
import my.projects.factory.domain.model.MachineTypeModel;
import my.projects.factory.domain.service.MachineTypeService;
import my.projects.factory.persistence.entity.MachineType;
import my.projects.factory.persistence.repository.MachineTypeRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementation of {@link MachineTypeService} that handles business logic
 * for MachineType entities. Uses {@link MachineTypeMapper} to convert between
 * {@link MachineType} entities and {@link MachineTypeModel} domain models.
 */
@Service
public class MachineTypeServiceImpl
        extends CrudServiceImpl<MachineTypeModel, MachineType, UUID>
        implements MachineTypeService {

    public MachineTypeServiceImpl(MachineTypeRepository repository, MachineTypeMapper mapper) {
        super(repository, mapper::toModel, mapper::toEntity);
    }
}

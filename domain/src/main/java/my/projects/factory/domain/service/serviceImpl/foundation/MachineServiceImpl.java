package my.projects.factory.domain.service.serviceImpl.foundation;

import my.projects.factory.domain.mapper.foundation.MachineMapper;
import my.projects.factory.domain.model.foundation.MachineModel;
import my.projects.factory.domain.service.foundation.MachineService;
import my.projects.factory.domain.service.serviceImpl.PageableServiceImpl;
import my.projects.factory.persistence.entity.foundation.Machine;
import my.projects.factory.persistence.repository.foundation.MachineRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementation of {@link MachineService} that handles business logic
 * for Machine entities. Uses {@link MachineMapper} to convert between
 * {@link Machine} entities and {@link MachineModel} domain models.
 */
@Service
public class MachineServiceImpl
        extends PageableServiceImpl<MachineModel, Machine, UUID>
        implements MachineService {

    public MachineServiceImpl(MachineRepository repository, MachineMapper mapper) {
        super(repository, mapper::toModel, mapper::toEntity);
    }
}
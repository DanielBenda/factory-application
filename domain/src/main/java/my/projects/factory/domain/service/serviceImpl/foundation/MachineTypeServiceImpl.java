package my.projects.factory.domain.service.serviceImpl.foundation;

import my.projects.factory.domain.filter.foundation.MachineTypeFilter;
import my.projects.factory.domain.mapper.foundation.MachineTypeMapper;
import my.projects.factory.domain.model.foundation.MachineTypeModel;
import my.projects.factory.domain.service.foundation.MachineTypeService;
import my.projects.factory.domain.service.serviceImpl.PageableServiceImpl;
import my.projects.factory.persistence.entity.foundation.MachineType;
import my.projects.factory.persistence.repository.foundation.MachineTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final MachineTypeRepository machineTypeRepository;
    private final MachineTypeMapper mapper;

    public MachineTypeServiceImpl(MachineTypeRepository repository, MachineTypeMapper mapper) {
        super(repository, mapper::toModel, mapper::toEntity);
        this.machineTypeRepository = repository;
        this.mapper = mapper;
    }

    /**
     * Returns a paginated list of {@link MachineTypeModel} filtered by the provided criteria.
     * <p>
     * If no filter is provided, all machine types are returned.
     * If a filter is present, the method performs a case-insensitive partial match
     * (contains) on either the {@code name} or {@code code} field.
     * </p>
     * @param filter   optional filter containing search criteria (nameQuery or codeQuery)
     * @param pageable pagination information (page number, size, sorting)
     * @return page of filtered and mapped {@link MachineTypeModel} instances
     */
    @Override
    public Page<MachineTypeModel> findMachineTypes(
            MachineTypeFilter filter,
            Pageable pageable
    ) {

        if (filter == null) {
            return machineTypeRepository.findAll(pageable).map(mapper::toModel);
        }

        String name = filter.getNameQuery();
        String code = filter.getCodeQuery();

        if (name != null && code != null) {
            return machineTypeRepository
                    .findByNameContainingIgnoreCaseAndCodeContainingIgnoreCase(name, code, pageable)
                    .map(mapper::toModel);
        }

        if (name != null) {
            return machineTypeRepository
                    .findByNameContainingIgnoreCase(name, pageable)
                    .map(mapper::toModel);
        }

        if (code != null) {
            return machineTypeRepository
                    .findByCodeContainingIgnoreCase(code, pageable)
                    .map(mapper::toModel);
        }

        return machineTypeRepository.findAll(pageable).map(mapper::toModel);
    }
}
package my.projects.factory.domain.mapper.foundation;

import my.projects.factory.domain.mapper.EntityMapper;
import my.projects.factory.domain.mapper.RepositoryFinder;
import my.projects.factory.domain.model.foundation.MachineModel;
import my.projects.factory.persistence.entity.foundation.Machine;
import my.projects.factory.persistence.entity.foundation.MachineType;
import my.projects.factory.persistence.repository.foundation.MachineTypeRepository;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between {@link Machine} entity and {@link MachineModel} domain model.
 * <p>
 * Provides methods to transform data from database entities to domain models and vice versa.
 */
@Component
public class MachineMapper implements EntityMapper<MachineModel, Machine> {

    private final MachineTypeRepository machineTypeRepository;

    public MachineMapper(MachineTypeRepository machineTypeRepository) {
        this.machineTypeRepository = machineTypeRepository;
    }

    /**
     * Converts a {@link Machine} entity to a {@link MachineModel}.
     *
     * @param machine the {@code Machine} entity to convert; must not be null
     * @return the corresponding {@code MachineModel} with id, code, machineType, name and year set
     */
    @Override
    public MachineModel toModel(Machine machine) {
        return MachineModel.builder()
                .id(machine.getId())
                .code(machine.getCode())
                .machineType(machine.getMachineTypeId().getName())
                .name(machine.getName())
                .year(machine.getYear())
                .build();
    }

    /**
     * Converts a {@link MachineModel} to a {@link Machine} entity.
     *
     * @param machineModel the {@code MachineModel} to convert; must not be null
     * @return a {@code Machine} entity with id, code, machineType, name and year set
     */
    @Override
    public Machine toEntity(MachineModel machineModel) {
        Machine machine = new Machine();
        machine.setCode(machineModel.code());
        machine.setMachineTypeId(resolveMachineType(machineModel.machineType()));
        machine.setName(machineModel.name());
        machine.setYear(machineModel.year());
        return machine;
    }

    /**
     * Converts machine type id (string) from model to MachineType entity.
     */
    private MachineType resolveMachineType(String machineTypeId) {
        return RepositoryFinder.resolveById(
                machineTypeId,
                machineTypeRepository,
                "MachineType"
        );
    }
}

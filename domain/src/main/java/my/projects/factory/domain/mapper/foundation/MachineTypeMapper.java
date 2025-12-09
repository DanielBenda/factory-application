package my.projects.factory.domain.mapper.foundation;

import my.projects.factory.domain.mapper.EntityMapper;
import my.projects.factory.domain.model.foundation.MachineTypeModel;
import my.projects.factory.persistence.entity.foundation.MachineType;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between {@link MachineType} entity and {@link MachineTypeModel} domain model.
 * <p>
 * Provides methods to transform data from database entities to domain models and vice versa.
 */
@Component
public class MachineTypeMapper implements EntityMapper<MachineTypeModel, MachineType> {

    /**
     * Converts a {@link MachineType} entity to a {@link MachineTypeModel}.
     *
     * @param machineType the entity to convert; must not be null
     * @return the corresponding {@code MachineTypeModel} with id, code, description and name set
     */
    @Override
    public MachineTypeModel toModel(MachineType machineType) {
        return MachineTypeModel.builder()
                .id(machineType.getId())
                .code(machineType.getCode())
                .description(machineType.getDescription())
                .name(machineType.getName())
                .build();
    }

    /**
     * Converts a {@link MachineTypeModel} to a {@link MachineType} entity.
     *
     * @param machineTypeModel the {@code MachineTypeModel} to convert; must not be null
     * @return a {@code MachineType} entity with id, code, description and name set
     */
    @Override
    public MachineType toEntity(MachineTypeModel machineTypeModel) {
        MachineType machineType = new MachineType();
        machineType.setId(machineTypeModel.id());
        machineType.setCode(machineTypeModel.code());
        machineType.setDescription(machineTypeModel.description());
        machineType.setName(machineTypeModel.name());
        return machineType;
    }
}

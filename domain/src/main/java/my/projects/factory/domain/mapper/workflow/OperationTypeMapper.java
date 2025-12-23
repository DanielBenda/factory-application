package my.projects.factory.domain.mapper.workflow;

import my.projects.factory.domain.mapper.EntityMapper;
import my.projects.factory.domain.model.workflow.OperationTypeModel;
import my.projects.factory.persistence.entity.workflow.OperationType;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between {@link OperationType} entity and {@link OperationTypeModel} domain model.
 * <p>
 * Provides methods to transform data from database entities to domain models and vice versa.
 */
@Component
public class OperationTypeMapper implements EntityMapper<OperationTypeModel, OperationType> {

    /**
     * Converts a {@link OperationType} entity to a {@link OperationTypeModel}.
     *
     * @param operationType the entity to convert; must not be null
     * @return the corresponding {@code OperationTypeModel} with id, description, name, created and createdBy set
     */
    @Override
    public OperationTypeModel toModel(OperationType operationType) {
        return OperationTypeModel.builder()
                .id(operationType.getId())
                .description(operationType.getDescription())
                .name(operationType.getName())
                .created(operationType.getCreated())
                .createdBy(operationType.getCreatedBy())
                .build();
    }

    /**
     * Converts a {@link OperationTypeModel} to a {@link OperationType} entity.
     *
     * @param operationTypeModel the {@code OperationTypeModel} to convert; must not be null
     * @return a {@code OperationType} entity with id, description, name, created and createdBy set
     */
    @Override
    public OperationType toEntity(OperationTypeModel operationTypeModel) {
        OperationType operationType = new OperationType();
        operationType.setId(operationTypeModel.id());
        operationType.setDescription(operationTypeModel.description());
        operationType.setName(operationTypeModel.name());
        operationType.setCreated(operationTypeModel.created());
        operationType.setCreatedBy(operationTypeModel.createdBy());
        return operationType;
    }
}

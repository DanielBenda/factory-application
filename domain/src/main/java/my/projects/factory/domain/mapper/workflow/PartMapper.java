package my.projects.factory.domain.mapper.workflow;

import my.projects.factory.domain.mapper.EntityMapper;
import my.projects.factory.domain.model.workflow.PartModel;
import my.projects.factory.persistence.entity.workflow.Part;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between {@link Part} entity and {@link PartModel} domain model.
 * <p>
 * Provides methods to transform data from database entities to domain models and vice versa.
 */
@Component
public class PartMapper implements EntityMapper<PartModel, Part> {

    /**
     * Converts a {@link Part} entity to a {@link PartModel}.
     *
     * @param part the entity to convert; must not be null
     * @return the corresponding {@code PartModel} with id, code, material and name set
     */
    @Override
    public PartModel toModel(Part part) {
        return PartModel.builder()
                .id(part.getId())
                .code(part.getCode())
                .name(part.getName())
                .material(part.getMaterial())
                .build();
    }

    /**
     * Converts a {@link PartModel} to a {@link Part} entity.
     *
     * @param partModel the {@code PartModel} to convert; must not be null
     * @return a {@code Part} entity with id, code, material and name set
     */
    @Override
    public Part toEntity(PartModel partModel) {
        Part part = new Part();
        part.setId(partModel.id());
        part.setCode(partModel.code());
        part.setMaterial(partModel.material());
        part.setName(partModel.name());
        return part;
    }
}

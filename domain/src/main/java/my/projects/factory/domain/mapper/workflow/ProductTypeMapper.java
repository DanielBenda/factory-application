package my.projects.factory.domain.mapper.workflow;

import my.projects.factory.domain.mapper.EntityMapper;
import my.projects.factory.domain.model.workflow.ProductTypeModel;
import my.projects.factory.persistence.entity.workflow.ProductType;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between {@link ProductType} entity and {@link ProductTypeModel} domain model.
 * <p>
 * Provides methods to transform data from database entities to domain models and vice versa.
 */
@Component
public class ProductTypeMapper implements EntityMapper<ProductTypeModel, ProductType> {

    /**
     * Converts a {@link ProductType} entity to a {@link ProductTypeModel}.
     *
     * @param productType the entity to convert; must not be null
     * @return the corresponding {@code ProductTypeModel} with id, code, created, createdBy, description and name set
     */
    @Override
    public ProductTypeModel toModel(ProductType productType) {
        return ProductTypeModel.builder()
                .id(productType.getId())
                .code(productType.getCode())
                .created(productType.getCreated())
                .createdBy(productType.getCreatedBy())
                .description(productType.getDescription())
                .name(productType.getName())
                .build();
    }

    /**
     * Converts a {@link ProductTypeModel} to a {@link ProductType} entity.
     *
     * @param productTypeModel the {@code ProductTypeModel} to convert; must not be null
     * @return a {@code ProductType} entity with id, code, created, createdBy, description and name set
     */
    @Override
    public ProductType toEntity(ProductTypeModel productTypeModel) {
        ProductType productType = new ProductType();
        productType.setId(productTypeModel.id());
        productType.setCode(productTypeModel.code());
        productType.setCreated(productTypeModel.created());
        productType.setCreatedBy(productTypeModel.createdBy());
        productType.setDescription(productTypeModel.description());
        productType.setName(productTypeModel.name());
        return productType;
    }
}

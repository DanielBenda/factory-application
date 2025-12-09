package my.projects.factory.domain.service.serviceImpl.workflow;

import my.projects.factory.domain.mapper.workflow.ProductTypeMapper;
import my.projects.factory.domain.model.workflow.ProductTypeModel;
import my.projects.factory.domain.service.serviceImpl.CrudServiceImpl;
import my.projects.factory.domain.service.workflow.ProductTypeService;
import my.projects.factory.persistence.entity.workflow.ProductType;
import my.projects.factory.persistence.repository.workflow.ProductTypeRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementation of {@link ProductTypeService} that handles business logic
 * for ProductType entities. Uses {@link ProductTypeMapper} to convert between
 * {@link ProductType} entities and {@link ProductTypeModel} domain models.
 */
@Service
public class ProductTypeServiceImpl
        extends CrudServiceImpl<ProductTypeModel, ProductType, UUID>
        implements ProductTypeService {

    protected ProductTypeServiceImpl(ProductTypeRepository repository, ProductTypeMapper mapper) {
        super(repository, mapper::toModel, mapper::toEntity);
    }
}

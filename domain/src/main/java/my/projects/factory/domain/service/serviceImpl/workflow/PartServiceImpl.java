package my.projects.factory.domain.service.serviceImpl.workflow;

import my.projects.factory.domain.mapper.workflow.PartMapper;
import my.projects.factory.domain.model.workflow.PartModel;
import my.projects.factory.domain.service.serviceImpl.PageableServiceImpl;
import my.projects.factory.domain.service.workflow.PartService;
import my.projects.factory.persistence.entity.workflow.Part;
import my.projects.factory.persistence.repository.workflow.PartRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementation of {@link PartService} that handles business logic
 * for Part entities. Uses {@link PartMapper} to convert between
 * {@link Part} entities and {@link PartModel} domain models.
 */
@Service
public class PartServiceImpl
        extends PageableServiceImpl<PartModel, Part, UUID>
        implements PartService {

    protected PartServiceImpl(PartRepository repository, PartMapper mapper) {
        super(repository, mapper::toModel, mapper::toEntity);
    }
}

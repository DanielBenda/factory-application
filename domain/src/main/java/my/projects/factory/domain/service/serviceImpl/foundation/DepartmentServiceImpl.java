package my.projects.factory.domain.service.serviceImpl.foundation;

import my.projects.factory.domain.mapper.foundation.DepartmentMapper;
import my.projects.factory.domain.model.foundation.DepartmentModel;
import my.projects.factory.domain.service.foundation.DepartmentService;
import my.projects.factory.domain.service.serviceImpl.CrudServiceImpl;
import my.projects.factory.persistence.entity.Department;
import my.projects.factory.persistence.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementation of {@link DepartmentService} that handles business logic
 * for Department entities. Uses {@link DepartmentMapper} to convert between
 * {@link Department} entities and {@link DepartmentModel} domain models.
 */
@Service
public class DepartmentServiceImpl
        extends CrudServiceImpl<DepartmentModel, Department, UUID>
        implements DepartmentService {

    public DepartmentServiceImpl(DepartmentRepository repository, DepartmentMapper mapper) {
        super(repository, mapper::toModel, mapper::toEntity);
    }
}

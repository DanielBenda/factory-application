package my.projects.factory.domain.service.serviceImpl;

import my.projects.factory.persistence.entity.Department;
import my.projects.factory.domain.mapper.DepartmentMapper;
import my.projects.factory.domain.model.DepartmentModel;
import org.springframework.stereotype.Service;
import my.projects.factory.persistence.repository.DepartmentRepository;
import my.projects.factory.domain.service.DepartmentService;

import java.util.UUID;

@Service
public class DepartmentServiceImpl
        extends CrudServiceImpl<DepartmentModel, Department, UUID>
        implements DepartmentService {

    public DepartmentServiceImpl(DepartmentRepository repository, DepartmentMapper mapper) {
        super(repository, mapper::toModel, mapper::toEntity);
    }
}

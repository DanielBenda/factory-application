package service.serviceImpl;

import entity.Department;
import mapper.DepartmentMapper;
import model.DepartmentModel;
import org.springframework.stereotype.Service;
import repository.DepartmentRepository;
import service.DepartmentService;

import java.util.UUID;

@Service
public class DepartmentServiceImpl
        extends CrudServiceImpl<DepartmentModel, Department, UUID>
        implements DepartmentService {

    public DepartmentServiceImpl(DepartmentRepository repository, DepartmentMapper mapper) {
        super(repository, mapper::toModel, mapper::toEntity);
    }
}

package service.serviceImpl;

import entity.DepartmentEntity;
import mapper.DepartmentMapper;
import model.Department;
import org.springframework.stereotype.Service;
import repository.DepartmentRepository;
import service.DepartmentService;

import java.util.UUID;

@Service
public class DepartmentServiceImpl
        extends CrudServiceImpl<Department, DepartmentEntity, UUID>
        implements DepartmentService {

    private final DepartmentRepository repository;
    private final DepartmentMapper mapper;

    public DepartmentServiceImpl(DepartmentRepository repository, DepartmentMapper mapper) {
        super(repository, mapper::toModel, mapper::toEntity);
        this.repository = repository;
        this.mapper = mapper;
    }
}

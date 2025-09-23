package mapper;

import entity.DepartmentEntity;
import model.Department;

public class DepartmentMapper implements EntityMapper<Department, DepartmentEntity> {

    @Override
    public Department toModel(DepartmentEntity department){
        return Department.builder()
                .id(department.getId())
                .code(department.getCode())
                .leader(department.getLeader())
                .name(department.getName())
                .build();
    }

    @Override
    public DepartmentEntity toEntity(Department department){
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(department.id());
        departmentEntity.setCode(department.code());
        departmentEntity.setLeader(department.leader());
        departmentEntity.setName(department.name());
        return departmentEntity;
    }
}

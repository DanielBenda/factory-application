package mapper;

import entity.Department;
import model.DepartmentModel;

public class DepartmentMapper implements EntityMapper<DepartmentModel, Department> {

    @Override
    public DepartmentModel toModel(Department department){
        return DepartmentModel.builder()
                .id(department.getId())
                .code(department.getCode())
                .leader(department.getLeader())
                .name(department.getName())
                .build();
    }

    @Override
    public Department toEntity(DepartmentModel departmentModel){
        Department department = new Department();
        department.setId(departmentModel.id());
        department.setCode(departmentModel.code());
        department.setLeader(departmentModel.leader());
        department.setName(departmentModel.name());
        return department;
    }
}

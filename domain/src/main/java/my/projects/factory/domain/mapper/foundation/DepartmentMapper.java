package my.projects.factory.domain.mapper.foundation;

import my.projects.factory.domain.mapper.EntityMapper;
import my.projects.factory.domain.model.foundation.DepartmentModel;
import my.projects.factory.persistence.entity.Department;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between {@link Department} entity and {@link DepartmentModel} domain model.
 * <p>
 * Provides methods to transform data from database entities to domain models and vice versa.
 */
@Component
public class DepartmentMapper implements EntityMapper<DepartmentModel, Department> {

    /**
     * Converts a {@link Department} entity to a {@link DepartmentModel}.
     *
     * @param department the entity to convert; must not be null
     * @return the corresponding {@code DepartmentModel} with id, code, leader and name set
     */
    @Override
    public DepartmentModel toModel(Department department) {
        return DepartmentModel.builder()
                .id(department.getId())
                .code(department.getCode())
                .leader(department.getLeader())
                .name(department.getName())
                .build();
    }

    /**
     * Converts a {@link DepartmentModel} to a {@link Department} entity.
     *
     * @param departmentModel the {@code DepartmentModel} to convert; must not be null
     * @return a {@code Department} entity with id, code, leader and name set
     */
    @Override
    public Department toEntity(DepartmentModel departmentModel) {
        Department department = new Department();
        department.setId(departmentModel.id());
        department.setCode(departmentModel.code());
        department.setLeader(departmentModel.leader());
        department.setName(departmentModel.name());
        return department;
    }
}

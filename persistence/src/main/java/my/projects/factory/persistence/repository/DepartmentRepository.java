package my.projects.factory.persistence.repository;

import my.projects.factory.persistence.entity.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, UUID> {
}

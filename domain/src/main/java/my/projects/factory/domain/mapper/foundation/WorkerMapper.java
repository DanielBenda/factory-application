package my.projects.factory.domain.mapper.foundation;

import my.projects.factory.domain.mapper.EntityMapper;
import my.projects.factory.domain.mapper.RepositoryFinder;
import my.projects.factory.persistence.entity.foundation.Department;
import my.projects.factory.persistence.entity.foundation.Worker;
import my.projects.factory.domain.model.foundation.WorkerModel;
import my.projects.factory.persistence.repository.foundation.DepartmentRepository;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between {@link Worker} entity and {@link WorkerModel} domain model.
 * <p>
 * Provides methods to transform data from database entities to domain models and vice versa.
 */
@Component
public class WorkerMapper implements EntityMapper<WorkerModel, Worker> {

    private final DepartmentRepository departmentRepository;

    public WorkerMapper(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    /**
     * Converts a {@link Worker} entity to a {@link WorkerModel}.
     *
     * @param worker the {@code Worker} entity to convert; must not be null
     * @return the corresponding {@code WorkerModel} with id, name, department, surname and workPosition set
     */
    @Override
    public WorkerModel toModel(Worker worker) {
        return WorkerModel.builder()
                .id(worker.getId())
                .created(worker.getCreated())
                .createdBy(worker.getCreatedBy())
                .department(worker.getDepartmentId().getName())
                .name(worker.getName())
                .surname(worker.getSurname())
                .systemRole(worker.getSystemRole())
                .workPosition(worker.getWorkPosition())
                .build();
    }

    /**
     * Converts a {@link WorkerModel} to a {@link Worker} entity.
     *
     * @param workerModel the {@code WorkerModel} to convert; must not be null
     * @return a {@code Worker} entity with id, name, surname and workPosition set
     */
    @Override
    public Worker toEntity(WorkerModel workerModel) {
        Worker worker = new Worker();
        worker.setId(workerModel.id());
        worker.setCreated(workerModel.created());
        worker.setCreatedBy(workerModel.createdBy());
        worker.setDepartmentId(resolveDepartment(workerModel.department()));
        worker.setName(workerModel.name());
        worker.setSurname(workerModel.surname());
        worker.setSystemRole(workerModel.systemRole());
        worker.setWorkPosition(workerModel.workPosition());
        return worker;
    }

    /**
     * Converts department id (string) from model to Department entity.
     */
    private Department resolveDepartment(String departmentId) {
        return RepositoryFinder.resolveById(
                departmentId,
                departmentRepository,
                "Department"
        );
    }
}

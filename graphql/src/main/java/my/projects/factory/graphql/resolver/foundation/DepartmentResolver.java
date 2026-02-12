package my.projects.factory.graphql.resolver.foundation;

import my.projects.factory.domain.model.foundation.DepartmentModel;
import my.projects.factory.domain.service.foundation.DepartmentService;
import my.projects.factory.generated.GqlCreateDepartmentInput;
import my.projects.factory.generated.GqlDepartment;
import my.projects.factory.generated.GqlUpdateDepartmentInput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

/**
 * GraphQL resolver for operations on Department entities.
 * <p>
 * Provides queries and mutations for fetching, creating, and deleting departments.
 * Converts between {@link DepartmentModel} and {@link GqlDepartment} objects.
 */
@Controller
public class DepartmentResolver {

    private final DepartmentService departmentService;

    /**
     * Constructor.
     *
     * @param departmentService the service providing business logic for departments
     */
    public DepartmentResolver(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * Returns all workers.
     *
     * @return pageable of all workers as {@link GqlDepartment} objects
     */
    @QueryMapping(name = "departments")
    public Page<DepartmentModel> departments(
            @Argument int page,
            @Argument int size
    ) {
        return departmentService.findPage(PageRequest.of(page, size));
    }

    /**
     * Resolves the {@code items} field of {@code DepartmentPage}.
     *
     * @param page Spring Data page returned by the parent query
     * @return list of departments for the current page
     */
    @SchemaMapping(typeName = "DepartmentPage", field = "items")
    public List<GqlDepartment> departmentPageItems(Page<DepartmentModel> page) {
        return page.getContent()
                .stream()
                .map(this::toGql)
                .toList();
    }

    /**
     * Resolves the {@code totalCount} field of {@code DepartmentPage}.
     *
     * @param page Spring Data page returned by the parent query
     * @return total number of departments across all pages
     */
    @SchemaMapping(typeName = "DepartmentPage", field = "totalCount")
    public Long departmentPageTotalCount(Page<DepartmentModel> page) {
        return page.getTotalElements();
    }

    /**
     * Returns a single department by ID.
     *
     * @param id the UUID of the department
     * @return the department as a {@link GqlDepartment} object
     * @throws RuntimeException if the department is not found
     */
    @QueryMapping(name = "department")
    public GqlDepartment departmentById(@Argument UUID id) {
        return departmentService.findById(id)
                .map(this::toGql)
                .orElseThrow(() -> new RuntimeException("Department not found" + id));
    }

    /**
     * Creates a new department.
     *
     * @param input with parameters for creating a department
     * @return the created department as {@link GqlDepartment}
     */
    @MutationMapping
    public GqlDepartment createDepartment(@Argument GqlCreateDepartmentInput input) {
        DepartmentModel department = DepartmentModel.builder()
                .code(input.getCode())
                .leader(input.getLeader())
                .name(input.getName())
                .build();
        DepartmentModel created = departmentService.create(department);
        return toGql(created);
    }

    /**
     * Deletes a department by ID.
     *
     * @param id the UUID of the department to delete
     * @return true if deletion was successful
     */
    @MutationMapping
    public Boolean deleteDepartment(@Argument UUID id) {
        departmentService.delete(id);
        return true;
    }

    /**
     * Updates an existing department.
     * <p>
     * Fetches the current department by ID, applies only non-null fields from the
     * GraphQL input (PATCH-style update), and forwards the updated domain model
     * to the service layer. Returns the updated department as a GraphQL type.
     *
     * @param id    the UUID of the department to update
     * @param input the fields to update (nullable values are ignored)
     * @return the updated department in GraphQL representation
     * @throws RuntimeException if the department with the given ID does not exist
     */
    @MutationMapping
    public GqlDepartment updateDepartment(@Argument UUID id, @Argument GqlUpdateDepartmentInput input) {

        DepartmentModel existing = departmentService.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found: " + id));

        DepartmentModel updatedDepartment = DepartmentModel.builder()
                .id(existing.id())
                .code(input.getCode() != null ? input.getCode() : existing.code())
                .leader(input.getLeader() != null ? input.getLeader() : existing.leader())
                .name(input.getName() != null ? input.getName() : existing.name())
                .build();
        DepartmentModel saved = departmentService.update(id, updatedDepartment);

        return toGql(saved);
    }

    /**
     * Converts a {@link DepartmentModel} to a {@link GqlDepartment}.
     *
     * @param departmentModel the DepartmentModel to convert
     * @return the corresponding GqlDepartment, or null if input is null
     */
    private GqlDepartment toGql(DepartmentModel departmentModel) {
        if (departmentModel == null) {
            return null;
        }
        return GqlDepartment.builder()
                .withId(departmentModel.id())
                .withCode(departmentModel.code())
                .withLeader(departmentModel.leader())
                .withName(departmentModel.name())
                .build();
    }
}

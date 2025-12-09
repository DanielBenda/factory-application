package my.projects.factory.domain.model.foundation;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Builder;

import java.util.UUID;

/**
 * Domain model representing a department.
 * <p>
 * This record is used in the business logic and GraphQL API.
 * It is validated to ensure that required fields are not blank.
 *
 * @param id     Unique identifier of the department, must not be null.
 * @param code   Code of the department, must not be blank.
 * @param leader Leader of the department.
 * @param name   Name of the department, must not be blank.
 */
@Builder
public record DepartmentModel(@Nonnull UUID id, @Nonnull String code, @Nullable String leader, @Nonnull String name) {

    public DepartmentModel {
        if (code.isBlank()) {
            throw new IllegalArgumentException("Code must not be blank");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name of department must not be blank");
        }
    }
}

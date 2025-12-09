package my.projects.factory.domain.model.foundation;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Builder;

import java.util.UUID;

/**
 * Domain model representing a machine type.
 * <p>
 * This record is used in the business logic and GraphQL API.
 * It is validated to ensure that required fields are not blank.
 *
 * @param id          Unique identifier of the machine type, must not be null.
 * @param code        Code of the machine type, must not be blank.
 * @param description Description of the machine type.
 * @param name        Name of the machine type, must not be blank.
 */
@Builder
public record MachineTypeModel(@Nonnull UUID id, @Nonnull String code, @Nullable String description,
                               @Nonnull String name) {

    public MachineTypeModel {
        if (code.isBlank()) {
            throw new IllegalArgumentException("Code must not be blank");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name name must not be blank");
        }
    }
}

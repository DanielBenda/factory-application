package my.projects.factory.domain.model.workflow;

import jakarta.annotation.Nonnull;
import lombok.Builder;

import java.util.UUID;

/**
 * Domain model representing a part type.
 * <p>
 * This record is used in the business logic and GraphQL API.
 * It is validated to ensure that required fields are not blank.
 *
 * @param id       Unique identifier of the part, must not be null.
 * @param code     Code of the part, must not be blank.
 * @param material Type of the material of a part, must not be blank.
 * @param name     Name of the part, must not be blank.
 */
@Builder
public record PartModel(@Nonnull UUID id, @Nonnull String code, @Nonnull String name, @Nonnull String material) {

    public PartModel {
        if (code.isBlank()) {
            throw new IllegalArgumentException("Code must not be blank");
        }
        if (material.isBlank()) {
            throw new IllegalArgumentException("Material must not be blank");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
    }
}

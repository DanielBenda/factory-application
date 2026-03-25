package my.projects.factory.domain.model.workflow;

import jakarta.annotation.Nonnull;
import lombok.Builder;

import java.util.Date;
import java.util.UUID;

/**
 * Domain model representing an operation type.
 * <p>
 * This record is used in the business logic and GraphQL API.
 * It is validated to ensure that required fields are not blank.
 *
 * @param id          Unique identifier of the operation type, must not be null.
 * @param code        Code of the operation type, must not be null.
 * @param description Description of the operation type.
 * @param name        Name of the operation type, must not be blank.
 * @param created     Date of the creation of the operation type, must not be blank.
 * @param createdBy   Name of the creator of the operation type, must not be blank.
 */
@Builder
public record OperationTypeModel(@Nonnull UUID id, @Nonnull String code, String description, @Nonnull String name,
                                 @Nonnull Date created, @Nonnull String createdBy) {
    public OperationTypeModel {
        if (code.isBlank()) {
            throw new IllegalArgumentException("Code must not be blank");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        if (createdBy.isBlank()) {
            throw new IllegalArgumentException("Creator must not be blank");
        }
    }
}

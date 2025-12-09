package my.projects.factory.domain.model.workflow;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Builder;

import java.util.Date;
import java.util.UUID;

/**
 * Domain model representing a product type.
 * <p>
 * This record is used in the business logic and GraphQL API.
 * It is validated to ensure that required fields are not blank.
 *
 * @param id          Unique identifier of the product type, must not be null.
 * @param code        Code of the product type, must not be blank.
 * @param created     Date of creating the product type, must not be blank.
 * @param createdBy   Creator of the product type, must not be blank.
 * @param description Description of the product type.
 * @param name        Name of the product type, must not be blank.
 */
@Builder
public record ProductTypeModel(@Nonnull UUID id, @Nonnull String code, @Nonnull Date created, @Nonnull String createdBy,
                               @Nullable String description, @Nonnull String name) {

    public ProductTypeModel {
        if (createdBy.isBlank()) {
            throw new IllegalArgumentException("Creator must not be blank");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name of product type must not be blank");
        }
    }
}

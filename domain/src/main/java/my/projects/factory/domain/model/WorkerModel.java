package my.projects.factory.domain.model;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Builder;

import java.util.Date;
import java.util.UUID;

/**
 * Domain model representing a worker.
 * <p>
 * This record is used in the business logic and GraphQL API.
 * It is validated to ensure that required fields are not blank.
 *
 * @param id           Unique identifier of the worker, must not be null.
 * @param department   Name of the worker's department, must not be blank.
 * @param created      Date of the creating a worker, must not be blank.
 * @param createdBy    Creator of a worker, must not be blank.
 * @param name         First name of the worker, must not be blank.
 * @param surname      Surname of the worker, must not be blank.
 * @param systemRole   Role of the worker, must not be blank.
 * @param workPosition Job position of the worker
 */
@Builder
public record WorkerModel(@Nonnull UUID id, @Nonnull Date created, @Nonnull String createdBy,
                          @Nonnull String department, @Nonnull String name, @Nonnull String surname,
                          @Nonnull String systemRole, @Nullable String workPosition) {

    public WorkerModel {
        if (createdBy.isBlank()) {
            throw new IllegalArgumentException("Creator must not be blank");
        }
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        if (surname.isBlank()) {
            throw new IllegalArgumentException("Surname must not be blank");
        }
        if (systemRole.isBlank()) {
            throw new IllegalArgumentException("System role must not be blank");
        }
    }
}
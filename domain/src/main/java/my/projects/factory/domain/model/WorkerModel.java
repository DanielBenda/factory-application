package my.projects.factory.domain.model;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Builder;

import java.util.UUID;

/**
 * Domain model representing a worker.
 * <p>
 * This record is used in the business logic and GraphQL API.
 * It is validated to ensure that required fields are not blank.
 *
 * @param id           Unique identifier of the worker, must not be null.
 * @param name         First name of the worker, must not be blank.
 * @param surname      Surname of the worker, must not be blank.
 * @param department   Name of the worker's department, can be null.
 * @param workPosition Job position of the worker, must not be blank.
 */
@Builder
public record WorkerModel(@Nonnull UUID id, @Nonnull String name, @Nonnull String surname, @Nullable String department,
                          @Nonnull String workPosition) {

    public WorkerModel {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        if (surname.isBlank()) {
            throw new IllegalArgumentException("Surname must not be blank");
        }
        if (workPosition.isBlank()) {
            throw new IllegalArgumentException("WorkPosition must not be blank");
        }
    }
}
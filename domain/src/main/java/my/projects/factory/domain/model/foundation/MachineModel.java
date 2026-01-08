package my.projects.factory.domain.model.foundation;

import jakarta.annotation.Nonnull;
import lombok.Builder;

import java.util.UUID;

/**
 * Domain model representing a machine.
 * <p>
 * This record is used in the business logic and GraphQL API.
 * It is validated to ensure that required fields are not blank.
 *
 * @param id          Unique identifier of the worker, must not be null.
 * @param code        Code of the machine, must not be blank.
 * @param machineType Machine type of the machine, must not be blank.
 * @param name        Name of the machine.
 * @param year        Year of creating a machine.
 */
@Builder
public record MachineModel(@Nonnull UUID id, @Nonnull String code, @Nonnull String machineType, String name,
                           Integer year) {
    public MachineModel {
        if (code.isBlank()) {
            throw new IllegalArgumentException("Machine code must not be blank.");
        }
        if (machineType.isBlank()) {
            throw new IllegalArgumentException("Machine type must not be blank.");
        }
    }
}

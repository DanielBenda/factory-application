package my.projects.factory.domain.model;

import jakarta.annotation.Nullable;
import lombok.Builder;

import java.util.UUID;

@Builder
public record WorkerModel(UUID id, String name, String surname, @Nullable String department, String workPosition) {

    public WorkerModel {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        if (surname == null || surname.isBlank()) {
            throw new IllegalArgumentException("Surname must not be blank");
        }
        if (workPosition == null || workPosition.isBlank()) {
            throw new IllegalArgumentException("WorkPosition must not be blank");
        }
    }
}

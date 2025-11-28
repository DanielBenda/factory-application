package model;

import lombok.Builder;

import java.util.UUID;

@Builder
public record WorkerModel(UUID id, String name, String surname, UUID departmentId, String workPosition) {

    public WorkerModel {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        if (surname == null || surname.isBlank()) {
            throw new IllegalArgumentException("Surname must not be blank");
        }
        if (departmentId == null) {
            throw new IllegalArgumentException("model.Department ID must not be provided");
        }
            if (workPosition == null || workPosition.isBlank()) {
            throw new IllegalArgumentException("WorkPosition must not be blank");
        }
    }
}

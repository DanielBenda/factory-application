import lombok.Builder;

import java.util.UUID;

@Builder
public record WorkerBuilder(UUID id, String name, String surname, UUID departmentId, String workPosition) {

    public WorkerBuilder {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        if (surname == null || surname.isBlank()) {
            throw new IllegalArgumentException("Surname must not be blank");
        }
        if (departmentId == null) {
            throw new IllegalArgumentException("Department ID must not be provided");
        }
            if (workPosition == null || workPosition.isBlank()) {
            throw new IllegalArgumentException("WorkPosition must not be blank");
        }
    }
}

import lombok.Builder;

import java.util.UUID;

@Builder
public record DepartmentBuilder(UUID id, String code, String leader, String name) {

    public DepartmentBuilder {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Department code must not be blank");
        }
        if (leader == null || leader.isBlank()) {
            throw new IllegalArgumentException("Leader name must not be blank");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name name must not be blank");
        }
    }
}

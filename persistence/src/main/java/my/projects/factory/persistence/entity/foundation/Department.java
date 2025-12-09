package my.projects.factory.persistence.entity.foundation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import my.projects.factory.persistence.schema.foundation.DepartmentSchema;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * Represents a department in the system and maps to the {@code t_department} table in the database factory.
 * <p>
 * This entity is part of the persistence layer and should be used exclusively for database operations.
 * </p>
 * <p>
 * The entity implements {@link Serializable} to allow safe serialization in distributed systems
 * or caching frameworks.
 * </p>
 */
@Data
@Entity
@Table(name = DepartmentSchema.TABLE, schema = DepartmentSchema.SCHEMA)
public class Department implements Serializable {

    @Serial
    private static final long serialVersionUID = 4788014246625317298L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = DepartmentSchema.ID, nullable = false)
    private UUID id;

    @Column(name = DepartmentSchema.CODE, nullable = false, length = 50)
    private String code;

    @Column(name = DepartmentSchema.LEADER, length = 100)
    private String leader;

    @Column(name = DepartmentSchema.NAME, nullable = false, length = 100)
    private String name;

}
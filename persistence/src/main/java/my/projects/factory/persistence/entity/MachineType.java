package my.projects.factory.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import my.projects.factory.persistence.schema.MachineTypeSchema;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * Represents a machine type in the system and maps to the {@code t_machine_type} table in the database factory.
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
@Table(name = MachineTypeSchema.TABLE, schema = MachineTypeSchema.SCHEMA)
public class MachineType implements Serializable {

    @Serial
    private static final long serialVersionUID = 6538961630291529068L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = MachineTypeSchema.ID, nullable = false)
    private UUID id;

    @Column(name = MachineTypeSchema.CODE, nullable = false, length = 50)
    private String code;

    @Column(name = MachineTypeSchema.DESCRIPTION, length = Integer.MAX_VALUE)
    private String description;

    @Column(name = MachineTypeSchema.NAME, nullable = false, length = 100)
    private String name;
}
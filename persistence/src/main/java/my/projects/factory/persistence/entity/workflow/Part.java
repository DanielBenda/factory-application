package my.projects.factory.persistence.entity.workflow;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import my.projects.factory.persistence.schema.workflow.PartSchema;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * Represents a part in the system and maps to the {@code t_part} table in the database factory.
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
@Table(name = PartSchema.TABLE, schema = PartSchema.SCHEMA)
public class Part implements Serializable {

    @Serial
    private static final long serialVersionUID = -1693364303084656887L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = PartSchema.ID, nullable = false)
    private UUID id;

    @Column(name = PartSchema.CODE, nullable = false, length = 50)
    private String code;

    @Column(name = PartSchema.MATERIAL, nullable = false, length = 50)
    private String material;

    @Column(name = PartSchema.NAME, nullable = false, length = 100)
    private String name;
}


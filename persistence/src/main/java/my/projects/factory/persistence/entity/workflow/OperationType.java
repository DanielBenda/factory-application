package my.projects.factory.persistence.entity.workflow;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import my.projects.factory.persistence.schema.workflow.OperationTypeSchema;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Represents a part in the system and maps to the {@code t_operation_type} table in the database factory.
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
@Table(name = OperationTypeSchema.TABLE, schema = OperationTypeSchema.SCHEMA)
public class OperationType implements Serializable {

    @Serial
    private static final long serialVersionUID = 451763973240644459L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = OperationTypeSchema.ID, nullable = false)
    private UUID id;

    @Column(name = OperationTypeSchema.DESCRIPTION, length = Integer.MAX_VALUE)
    private String description;

    @Column(name = OperationTypeSchema.NAME, nullable = false, length = 100)
    private String name;

    @Column(name = OperationTypeSchema.CREATED, nullable = false)
    private Date created;

    @Column(name = OperationTypeSchema.CREATED_BY, nullable = false, length = 100)
    private String createdBy;

}
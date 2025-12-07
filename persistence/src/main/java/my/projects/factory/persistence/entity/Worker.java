package my.projects.factory.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import my.projects.factory.persistence.schema.WorkerSchema;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * Represents a worker in the system and maps to the {@code t_worker} table in the database factory.
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
@Table(name = WorkerSchema.TABLE, schema = WorkerSchema.SCHEMA)
public class Worker implements Serializable {

    @Serial
    private static final long serialVersionUID = 9005788893087876537L;

    @Id
    @Column(name = WorkerSchema.ID, nullable = false)
    private UUID id;

    @Column(name = WorkerSchema.NAME, nullable = false, length = 100)
    private String name;

    @Column(name = WorkerSchema.SURNAME, nullable = false, length = 100)
    private String surname;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = WorkerSchema.DEPARTMENT, nullable = false)
    private Department departmentId;

    @Column(name = WorkerSchema.WORK_POSITION, length = 100)
    private String workPosition;
}
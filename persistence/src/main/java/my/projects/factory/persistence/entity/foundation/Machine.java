package my.projects.factory.persistence.entity.foundation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import my.projects.factory.persistence.schema.foundation.MachineSchema;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * Represents a machine in the system and maps to the {@code t_machine} table in the database factory.
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
@Table(name = MachineSchema.TABLE, schema = MachineSchema.SCHEMA)
public class Machine implements Serializable {

    @Serial
    private static final long serialVersionUID = 277964326682920748L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = MachineSchema.ID, nullable = false)
    private UUID id;

    @Column(name = MachineSchema.CODE, nullable = false, length = 50)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = MachineSchema.MACHINE_TYPE, nullable = false)
    private MachineType machineTypeId;

    @Column(name = MachineSchema.NAME, length = 100)
    private String name;

    @Column(name = MachineSchema.YEAR)
    private Integer year;

}
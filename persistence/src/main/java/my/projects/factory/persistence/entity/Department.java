package my.projects.factory.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import my.projects.factory.persistence.schema.DepartmentSchema;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = DepartmentSchema.TABLE, schema = DepartmentSchema.SCHEMA)
public class Department implements Serializable {

    @Serial
    private static final long serialVersionUID = 4788014246625317298L;

    @Id
    @Column(name = DepartmentSchema.ID, nullable = false)
    private UUID id;

    @Column(name = DepartmentSchema.CODE, nullable = false, length = 50)
    private String code;

    @Column(name = DepartmentSchema.LEADER, length = 100)
    private String leader;

    @Column(name = DepartmentSchema.NAME, nullable = false, length = 100)
    private String name;

}
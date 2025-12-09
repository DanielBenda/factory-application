package my.projects.factory.persistence.entity.workflow;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import my.projects.factory.persistence.schema.workflow.ProductTypeSchema;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Represents a product type in the system and maps to the {@code t_product_type} table in the database factory.
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
@Table(name = ProductTypeSchema.TABLE, schema = ProductTypeSchema.SCHEMA)
public class ProductType implements Serializable {

    @Serial
    private static final long serialVersionUID = 3002759635634001023L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = ProductTypeSchema.ID, nullable = false)
    private UUID id;

    @Column(name = ProductTypeSchema.CODE, nullable = false, length = 50)
    private String code;

    @Column(name = ProductTypeSchema.CREATED, nullable = false)
    private Date created;

    @Column(name = ProductTypeSchema.CREATED_BY, nullable = false, length = 50)
    private String createdBy;

    @Column(name = ProductTypeSchema.DESCRIPTION)
    private String description;

    @Column(name = ProductTypeSchema.NAME, nullable = false, length = 100)
    private String name;
}
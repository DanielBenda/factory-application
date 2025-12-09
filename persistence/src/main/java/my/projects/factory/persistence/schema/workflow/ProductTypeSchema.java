package my.projects.factory.persistence.schema.workflow;

/**
 * Database schema constants for the {@code t_product_type} table.
 * <p>
 * Contains table name, schema name, and column names for the ProductType entity.
 */
public class ProductTypeSchema {

    public static final String TABLE = "t_product_type";
    public static final String SCHEMA = "factory";
    public static final String ID = "id";
    public static final String CODE = "code";
    public static final String CREATED = "created";
    public static final String CREATED_BY = "created_by";
    public static final String DESCRIPTION = "description";
    public static final String NAME = "name";

    private ProductTypeSchema() {}
}
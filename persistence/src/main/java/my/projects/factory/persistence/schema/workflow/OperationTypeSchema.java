package my.projects.factory.persistence.schema.workflow;

/**
 * Database schema constants for the {@code t_operation_type} table.
 * <p>
 * Contains table name, schema name, and column names for the OperationType entity.
 */
public class OperationTypeSchema {

    public static final String TABLE = "t_operation_type";
    public static final String SCHEMA = "factory";
    public static final String ID = "id";
    public static final String CODE = "code";
    public static final String DESCRIPTION = "description";
    public static final String NAME = "name";
    public static final String CREATED = "created";
    public static final String CREATED_BY = "created_by";

    private OperationTypeSchema() {}
}

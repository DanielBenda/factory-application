package my.projects.factory.persistence.schema;

/**
 * Database schema constants for the {@code t_machine_type} table.
 * <p>
 * Contains table name, schema name, and column names for the MachineType entity.
 */
public class MachineTypeSchema {

    public static final String TABLE = "t_machine_type";
    public static final String SCHEMA = "factory";
    public static final String ID = "id";
    public static final String CODE = "code";
    public static final String DESCRIPTION = "description";
    public static final String NAME = "name";

    private MachineTypeSchema() {}
}

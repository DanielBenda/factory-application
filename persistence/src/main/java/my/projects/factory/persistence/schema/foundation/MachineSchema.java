package my.projects.factory.persistence.schema.foundation;

/**
 * Database schema constants for the {@code t_machine} table.
 * <p>
 * Contains table name, schema name, and column names for the Machine entity.
 */
public class MachineSchema {

    public static final String TABLE = "t_machine";
    public static final String SCHEMA = "factory";
    public static final String ID = "id";
    public static final String CODE = "code";
    public static final String MACHINE_TYPE = "machine_type_id";
    public static final String NAME = "name";
    public static final String PRODUCTION_YEAR = "production_Year";

    private MachineSchema() {}
}

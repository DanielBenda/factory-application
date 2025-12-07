package my.projects.factory.persistence.schema;

/**
 * Database schema constants for the {@code t_worker} table.
 * <p>
 * Contains table name, schema name, and column names for the Worker entity.
 */
public class WorkerSchema {

    public static final String TABLE = "t_worker";
    public static final String SCHEMA = "factory";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String DEPARTMENT = "department_id";
    public static final String WORK_POSITION = "work_position";

    private WorkerSchema() {}
}
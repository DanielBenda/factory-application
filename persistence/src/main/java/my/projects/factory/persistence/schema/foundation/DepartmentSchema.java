package my.projects.factory.persistence.schema.foundation;

/**
 * Database schema constants for the {@code t_department} table.
 * <p>
 * Contains table name and column names for the Department entity.
 */
public class DepartmentSchema {

    public static final String TABLE = "t_department";
    public static final String ID = "id";
    public static final String CODE = "code";
    public static final String LEADER = "leader";
    public static final String NAME = "name";

    private DepartmentSchema() {}
}
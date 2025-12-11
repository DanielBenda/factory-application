package my.projects.factory.persistence.schema.workflow;

/**
 * Database schema constants for the {@code t_part} table.
 * <p>
 * Contains table name, schema name, and column names for the Part entity.
 */
public class PartSchema {

    public static final String TABLE = "t_part";
    public static final String SCHEMA = "factory";
    public static final String ID = "id";
    public static final String CODE = "code";
    public static final String MATERIAL = "material";
    public static final String NAME = "name";

    private PartSchema() {}
}

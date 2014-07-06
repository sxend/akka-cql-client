package arimitsu.sf.cql.v3.messages;

import arimitsu.sf.cql.v3.columntype.ColumnType;

/**
 * Created by sxend on 14/07/06.
 */
public class ColumnSpec {
    // (<ksname><tablename>)?<name><type>
    public final String keySpace;
    public final String tableName;
    public final String columnName;
    public final ColumnType columnType;

    public ColumnSpec(String keySpace, String tableName, String columnName, ColumnType columnType) {
        this.keySpace = keySpace;
        this.tableName = tableName;
        this.columnName = columnName;
        this.columnType = columnType;
    }
}

package arimitsu.sf.cql.v3.messages;

import arimitsu.sf.cql.v3.ColumnType;
import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sxend on 14/06/11.
 */
public class Metadata {
    // <flags><columns_count>[<paging_state>][<global_table_spec>?<col_spec_1>...<col_spec_n>]
    public final int flags;
    public final int columnsCount;
    public final byte[] pagingState;
    public final Map<String, String> globalTableSpec;
    public final List<ColumnSpec> columnSpec;

    public Metadata(int flags, int columnsCount, byte[] pagingState, Map<String, String> globalTableSpec, List<ColumnSpec> columnSpec) {
        this.flags = flags;
        this.columnsCount = columnsCount;
        this.pagingState = pagingState;
        this.globalTableSpec = globalTableSpec;
        this.columnSpec = columnSpec;
    }

    public static Metadata fromBuffer(ByteBuffer buffer) {
        int flags = buffer.getInt();
        int columnsCount = buffer.getInt();
        boolean hasPagingState = (MetadataFlags.HAS_MORE_PAGES.mask & flags) > 0;
        boolean hasGlobalSetting = (MetadataFlags.GLOBAL_TABLES_SPEC.mask & flags) > 0;
        boolean hasntMetadata = (MetadataFlags.NO_METADATA.mask & flags) > 0;
        byte[] pagingState = hasPagingState ? Notation.getBytes(buffer) : null;
        Map<String, String> globalTableSpec = new HashMap<>();
        if (hasGlobalSetting) {
            globalTableSpec.put("KEYSPACE", Notation.getString(buffer));
            globalTableSpec.put("TABLE_NAME", Notation.getString(buffer));
        }
        List<ColumnSpec> columnSpec = new ArrayList<>();
        for (int i = 0; i < columnsCount; i++) {
            String keySpace = null;
            String tableName = null;
            if (!hasGlobalSetting) {
                keySpace = Notation.getString(buffer);
                tableName = Notation.getString(buffer);
            }
            String columnName = Notation.getString(buffer);
            ColumnType columnType = ColumnType.Factory.fromBuffer(buffer);
            columnSpec.add(new ColumnSpec(keySpace, tableName, columnName, columnType));
        }
        return new Metadata(flags, columnsCount, pagingState, globalTableSpec, columnSpec);
    }

}

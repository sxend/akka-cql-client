package arimitsu.sf.cql.v3.messages;

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


    public static enum MetadataFlags {
        GLOBAL_TABLES_SPEC(0x0001),
        HAS_MORE_PAGES(0x0002),
        NO_METADATA(0x0004);

        public final int mask;

        MetadataFlags(int mask) {
            this.mask = mask;
        }

        public static MetadataFlags valueOf(int mask) {
            for (MetadataFlags f : values()) {
                if (f.mask == mask) return f;
            }
            throw new RuntimeException("invalid mask");
        }
    }

    public static class MetadataParser {
        public Metadata parse(ByteBuffer buffer) {
            int flags = buffer.getInt();
            int count = buffer.getInt();
            boolean hasPagingState = (MetadataFlags.HAS_MORE_PAGES.mask & flags) > 0;
            boolean hasGlobalSetting = (MetadataFlags.GLOBAL_TABLES_SPEC.mask & flags) > 0;
            boolean hasntMetadata = (MetadataFlags.NO_METADATA.mask & flags) > 0;
            byte[] pagingState = null;
            if (hasPagingState) {
                pagingState = Notation.getBytes(buffer);
            }
            Map<String, String> globalSetting = new HashMap<>();
            if (hasGlobalSetting) {
                globalSetting.put("KEYSPACE", Notation.getString(buffer));
                globalSetting.put("TABLE_NAME", Notation.getString(buffer));
            }
            List<ColumnSpec> columnSpecs = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                String keySpace = null;
                String tableName = null;
                if (!hasGlobalSetting) {
                    keySpace = Notation.getString(buffer);
                    tableName = Notation.getString(buffer);
                }
                String columnName = Notation.getString(buffer);
                ColumnType columnType = ColumnType.PARSER.parse(buffer);
                columnSpecs.add(new ColumnSpec(keySpace, tableName, columnName, columnType));
            }
            return new Metadata(flags, count, pagingState, globalSetting, columnSpecs);
        }
    }

    public static class ColumnSpec {
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
}

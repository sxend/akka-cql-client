package arimitsu.sf.cql.v3.messages.parser;

import arimitsu.sf.cql.v3.Parser;
import arimitsu.sf.cql.v3.messages.ColumnSpec;
import arimitsu.sf.cql.v3.messages.ColumnType;
import arimitsu.sf.cql.v3.messages.Metadata;
import arimitsu.sf.cql.v3.messages.MetadataFlags;
import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sxend on 14/07/06.
 */
public class MetadataParser implements Parser<Metadata> {
    private MetadataParser() {
    }

    public static final MetadataParser INSTANCE = new MetadataParser();

    @Override
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

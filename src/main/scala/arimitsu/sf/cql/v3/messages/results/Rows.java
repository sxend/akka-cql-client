package arimitsu.sf.cql.v3.messages.results;


import arimitsu.sf.cql.v3.messages.Metadata;
import arimitsu.sf.cql.v3.messages.Parser;
import arimitsu.sf.cql.v3.messages.Result;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sxend on 2014/06/11.
 */
public class Rows implements Result {
    // <metadata><rows_count><rows_content>
    public final Metadata metadata;
    public final int rowsCount;
    public final List<Map<String, Object>> rowsContent;

    public Rows(Metadata metadata, int rowsCount, List<Map<String, Object>> rowsContent) {
        this.metadata = metadata;
        this.rowsCount = rowsCount;
        this.rowsContent = rowsContent;
    }

    @Override
    public Kind getKind() {
        return Result.Kind.ROWS;
    }

    public static final Parser<Rows> PARSER = new Parser<Rows>() {
        @Override
        public Rows parse(ByteBuffer buffer) {
            Metadata metadata = Metadata.PARSER.parse(buffer);
            int rowsCount = buffer.getInt();
            List<Map<String, Object>> rowsContent = new ArrayList<>();
            for (int i = 0; i < rowsCount; i++) {
                Map<String, Object> map = new HashMap<>();
                for (int j = 0, columnCount = metadata.columnsCount; j < columnCount; j++) {
                    Metadata.ColumnSpec columnSpec = metadata.columnSpec.get(j);
                    Object result = columnSpec.columnType.getParser().parse(buffer);
                    map.put(columnSpec.columnName, result);
                }
                rowsContent.add(map);
            }
            return new Rows(metadata, rowsCount, rowsContent);
        }
    };
}

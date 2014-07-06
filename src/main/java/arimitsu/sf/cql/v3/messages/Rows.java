package arimitsu.sf.cql.v3.messages;


import arimitsu.sf.cql.v3.Column;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sxend on 2014/06/11.
 */
public class Rows implements Result {
    // <metadata><rows_count><rows_content>
    public final Metadata metadata;
    public final int rowsCount;
    public final List<List<Column>> rowsContent;

    public Rows(Metadata metadata, int rowsCount, List<List<Column>> rowsContent) {
        this.metadata = metadata;
        this.rowsCount = rowsCount;
        this.rowsContent = rowsContent;
    }

    @Override
    public Kind getKind() {
        return Result.Kind.ROWS;
    }

    public static Rows fromBuffer(ByteBuffer buffer) {
        Metadata metadata = Metadata.fromBuffer(buffer);
        int rowsCount = buffer.getInt();
        List<List<Column>> rowsContent = new ArrayList<>();
        for (int i = 0; i < rowsCount; i++) {
            List<Column> columns = new ArrayList<>();
            for (int j = 0, columnCount = metadata.columnsCount; j < columnCount; j++) {
                ColumnSpec columnSpec = metadata.columnSpec.get(j);
                Object result = columnSpec.columnType.getParser().parse(buffer);
                columns.add(new Column(columnSpec.columnName, result));
            }
            rowsContent.add(columns);
        }
        return new Rows(metadata, rowsCount, rowsContent);
    }
}

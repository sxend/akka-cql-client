package arimitsu.sf.cql.v3.messages.results;


import arimitsu.sf.cql.v3.messages.Metadata;
import arimitsu.sf.cql.v3.messages.Result;
import arimitsu.sf.cql.v3.util.Notation;

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
    public final List<Map<String, byte[]>> rowsContent;

    public Rows(Metadata metadata, int rowsCount, List<Map<String, byte[]>> rowsContent) {
        this.metadata = metadata;
        this.rowsCount = rowsCount;
        this.rowsContent = rowsContent;
    }

    @Override
    public Kind getKind() {
        return Result.Kind.ROWS;
    }

    private static final Metadata.MetadataParser metadataParser = new Metadata.MetadataParser();
    public static final ResultParser<Rows> PARSER = new ResultParser<Rows>() {
        @Override
        public Rows parse(ByteBuffer body) {
            Metadata metadata = metadataParser.parse(body);
            int rowsCount = body.getInt();
            List<Map<String, byte[]>> rowsContent = new ArrayList<>();
            for (int i = 0; i < rowsCount; i++) {
                Map<String, byte[]> map = new HashMap<>();
                for (int j = 0; j < metadata.columnsCount; j++) {
                    map.put(metadata.columnSpec.get(j).columnName, Notation.getBytes(body));
                }
                rowsContent.add(map);
            }
            return new Rows(metadata, rowsCount, rowsContent);
        }
    };
}

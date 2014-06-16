package arimitsu.sf.cql.v3.messages.results;


import arimitsu.sf.cql.v3.messages.ColumnType;
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

    private static final Metadata.MetadataParser metadataParser = new Metadata.MetadataParser();
    public static final ResultParser<Rows> PARSER = new ResultParser<Rows>() {
        @Override
        public Rows parse(ByteBuffer body) {
            Metadata metadata = metadataParser.parse(body);
            int rowsCount = body.getInt();
            List<Map<String, Object>> rowsContent = new ArrayList<>();
            for (int i = 0; i < rowsCount; i++) {
                Map<String, Object> map = new HashMap<>();
                for (int j = 0; j < metadata.columnsCount; j++) {
                    ColumnType columnType = metadata.columnSpec.get(j).columnType;
                    ColumnType.ColumnTypeEnum typeEnum = ColumnType.ColumnTypeEnum.valueOf(columnType.getId());
                    Object result = null;
                    switch (typeEnum) {
                        case CUSTOM:
                            break;
                        case ASCII:
                            result = new String(Notation.getBytes(body));
                            break;
                        case BIGINT:
                            break;
                        case BLOB:
                            break;
                        case BOOLEAN:
                            break;
                        case COUNTER:
                            break;
                        case DECIMAL:
                            break;
                        case DOUBLE:
                            break;
                        case FLOAT:
                            break;
                        case INT:
                            break;
                        case TIMESTAMP:
                            break;
                        case UUID:
                            break;
                        case VARCHAR:
                            break;
                        case VARINT:
                            break;
                        case TIMEUUID:
                            break;
                        case INET:
                            break;
                        case LIST:
                            break;
                        case MAP:
                            break;
                        case SET:
                            break;
                        case UDT:
                            break;
                        case TUPLE:
                            break;
                        default:
                            throw new RuntimeException("invalid type.");
                    }
                    map.put(metadata.columnSpec.get(j).columnName, result);
                }
                rowsContent.add(map);
            }
            return new Rows(metadata, rowsCount, rowsContent);
        }
    };
}

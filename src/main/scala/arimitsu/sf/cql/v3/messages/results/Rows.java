package arimitsu.sf.cql.v3.messages.results;

import arimitsu.sf.cql.v3.messages.Metadata;
import arimitsu.sf.cql.v3.messages.Result;
import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 2014/06/11.
 */
public class Rows implements Result {
    // <metadata><rows_count><rows_content>
    public final Object metadata;
    public final Object rowsCount;
    public final Object rowsContent;

    public Rows(Object metadata, Object rowsCount, Object rowsContent) {
        this.metadata = metadata;
        this.rowsCount = rowsCount;
        this.rowsContent = rowsContent;
    }

    @Override
    public Kind getKind() {
        return Kind.ROWS;
    }

    private static final Metadata.MetadataParser metadataParser= new Metadata.MetadataParser();
    public static final ResultParser<Rows> PARSER = new ResultParser<Rows>() {
        @Override
        public Rows parse(ByteBuffer body) {
            Metadata metadata = metadataParser.parse(body);
            int rowsCount =body.getInt();
            for(int i =0;i<rowsCount;i++){
                for(int j =0;j< metadata.columnsCount;j++){
                    Notation.getBytes(body);
                }
            }
            return null;
        }
    };
}

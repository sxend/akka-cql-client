package arimitsu.sf.cql.v3.messages.results;

import arimitsu.sf.cql.v3.messages.Result;
import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 2014/06/11.
 */
public class SchemaChange implements Result {
    public final ChangeType change;
    public final String keySpace;
    public final String table;

    public SchemaChange(String change, String keySpace, String table) {
        this.change = ChangeType.valueOf(change);
        this.keySpace = keySpace;
        this.table = table;
    }

    public static enum ChangeType {
        CREATED,
        UPDATED,
        DROPPED,;
    }

    @Override
    public Kind getKind() {
        return Kind.SCHEMA_CHANGE;
    }

    public static final ResultParser<SchemaChange> PARSER = new ResultParser<SchemaChange>() {
        @Override
        public SchemaChange parse(ByteBuffer body) {
            return new SchemaChange(Notation.getString(body), Notation.getString(body), Notation.getString(body));
        }
    };
}

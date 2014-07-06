package arimitsu.sf.cql.v3.messages;

import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 2014/06/11.
 */
public class SchemaChange implements Result, Event {
    public final ChangeType changeType;
    public final String keySpace;
    public final String table;

    public SchemaChange(ChangeType changeType, String keySpace, String table) {
        this.changeType = changeType;
        this.keySpace = keySpace;
        this.table = table;
    }

    @Override
    public EventType getType() {
        return EventType.SCHEMA_CHANGE;
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

    public static SchemaChange fromBuffer(ByteBuffer buffer) {
        ChangeType changeType = ChangeType.valueOf(Notation.getString(buffer));
        return new SchemaChange(changeType, Notation.getString(buffer), Notation.getString(buffer));
    }
}

package arimitsu.sf.cql.v3.messages;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/07.
 */
public interface Result {
    public abstract Kind getKind();

    public static enum Kind {
        VOID(0x0001),// : for results carrying no information.
        ROWS(0x0002),//: for results to select queries, returning a set of rows.
        SET_KEYSPACE(0x0003),//: the result to a `use` query.
        PREPARED(0x0004),//: result to a PREPARE message.
        SCHEMA_CHANGE(0x0005),;
        public final int code;// : the result to a schema altering query.

        Kind(int code) {
            this.code = code;
        }

        public static Kind valueOf(int code) {
            for (Kind r : values()) {
                if (r.code == code) return r;
            }
            throw new RuntimeException("invalid code");
        }
    }

    public static class Factory {
        public static Result fromBuffer(ByteBuffer buffer) {
            int code = buffer.getInt();
            Kind kind = Kind.valueOf(code);
            switch (kind) {
                case VOID:
                    return (Void) Void.fromBuffer(buffer);
                case ROWS:
                    return (Rows) Rows.fromBuffer(buffer);
                case PREPARED:
                    return (Prepared) Prepared.fromBuffer(buffer);
                case SET_KEYSPACE:
                    return (SetKeyspace) SetKeyspace.fromBuffer(buffer);
                case SCHEMA_CHANGE:
                    return (SchemaChange) SchemaChange.fromBuffer(buffer);
            }
            throw new RuntimeException("invalid kind code.");
        }
    }
}

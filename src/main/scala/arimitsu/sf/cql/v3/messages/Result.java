package arimitsu.sf.cql.v3.messages;

import arimitsu.sf.cql.v3.messages.results.Prepared;
import arimitsu.sf.cql.v3.messages.results.Rows;
import arimitsu.sf.cql.v3.messages.results.SchemaChange;
import arimitsu.sf.cql.v3.messages.results.SetKeyspace;
import arimitsu.sf.cql.v3.messages.results.Void;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/07.
 */
public interface Result {
    public Kind getKind();

    public static interface ResultParser<R extends Result> extends ResponseParser<R> {
    }


    public static enum Kind {
        VOID(0x0001, "Void", Void.PARSER),// : for results carrying no information.
        ROWS(0x0002, "Rows", Rows.PARSER),//: for results to select queries, returning a set of rows.
        SET_KEYSPACE(0x0003, "Set_keyspace", SetKeyspace.PARSER),//: the result to a `use` query.
        PREPARED(0x0004, "Prepared", Prepared.PARSER),//: result to a PREPARE message.
        SCHEMA_CHANGE(0x0005, "Schema_change", SchemaChange.PARSER),;
        public final int code;// : the result to a schema altering query.
        public final String name;
        private final ResultParser parser;

        Kind(int code, String name, ResultParser<? extends Result> parser) {
            this.code = code;
            this.name = name;
            this.parser = parser;
        }

        public static Kind valueOf(int code) {
            for (Kind r : values()) {
                if (r.code == code) return r;
            }
            throw new RuntimeException("invalid code");
        }
    }

    public static final ResponseParser<Result> Parser = new ResponseParser<Result>() {
        @Override
        public Result parse(ByteBuffer body) {
            int code = body.getInt();
            Kind kind = Kind.valueOf(code);
            switch (kind) {
                case VOID:
                    return (Void) Kind.VOID.parser.parse(body);
                case ROWS:
                    return (Rows) Kind.ROWS.parser.parse(body);
                case PREPARED:
                    return (Prepared) Kind.PREPARED.parser.parse(body);
                case SET_KEYSPACE:
                    return (SetKeyspace) Kind.SET_KEYSPACE.parser.parse(body);
                case SCHEMA_CHANGE:
                    return (SchemaChange) Kind.SCHEMA_CHANGE.parser.parse(body);
            }
            throw new RuntimeException("invalid kind code.");
        }
    };
}

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

    public static enum Kind {
        VOID(0x0001, Void.PARSER),// : for results carrying no information.
        ROWS(0x0002, Rows.PARSER),//: for results to select queries, returning a set of rows.
        SET_KEYSPACE(0x0003, SetKeyspace.PARSER),//: the result to a `use` query.
        PREPARED(0x0004, Prepared.PARSER),//: result to a PREPARE message.
        SCHEMA_CHANGE(0x0005, SchemaChange.PARSER),;
        public final int code;// : the result to a schema altering query.
        private final Parser<? extends Result> parser;

        Kind(int code, Parser<? extends Result> parser) {
            this.code = code;
            this.parser = parser;
        }

        public static Kind valueOf(int code) {
            for (Kind r : values()) {
                if (r.code == code) return r;
            }
            throw new RuntimeException("invalid code");
        }
    }

    public static final Parser<Result> PARSER = new Parser<Result>() {
        @Override
        public Result parse(ByteBuffer body) {
            int code = body.getInt();
            Kind kind = Kind.valueOf(code);
            return kind.parser.parse(body);
//            switch (kind) {
//                case VOID:
//                    return (Void) Kind.VOID.parser.parse(body);
//                case ROWS:
//                    return (Rows) Kind.ROWS.parser.parse(body);
//                case PREPARED:
//                    return (Prepared) Kind.PREPARED.parser.parse(body);
//                case SET_KEYSPACE:
//                    return (SetKeyspace) Kind.SET_KEYSPACE.parser.parse(body);
//                case SCHEMA_CHANGE:
//                    return (SchemaChange) Kind.SCHEMA_CHANGE.parser.parse(body);
//            }
//            throw new RuntimeException("invalid kind code.");
        }
    };
}

package arimitsu.sf.cql.v3.messages.results;

import arimitsu.sf.cql.v3.messages.Result;
import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;


/**
 * Created by sxend on 2014/06/11.
 */
public class SetKeyspace implements Result {

    public final String keySpace;

    public SetKeyspace(String keySpace) {
        this.keySpace = keySpace;
    }

    @Override
    public Kind getKind() {
        return Kind.SET_KEYSPACE;
    }

    public static final ResultParser<SetKeyspace> PARSER = new ResultParser<SetKeyspace>() {
        @Override
        public SetKeyspace parse(ByteBuffer body) {
            return new SetKeyspace(Notation.getString(body));
        }
    };
}

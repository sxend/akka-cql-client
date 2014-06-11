package arimitsu.sf.cql.v3.messages.results;

import arimitsu.sf.cql.v3.messages.Result;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 2014/06/11.
 */
public class Prepared implements Result {
    // <id><metadata><result_metadata>
    @Override
    public Kind getKind() {
        return Kind.PREPARED;
    }

    public static final ResultParser<Prepared> PARSER = new ResultParser<Prepared>() {
        @Override
        public Prepared parse(ByteBuffer body) {
            return new Prepared();
        }
    };
}

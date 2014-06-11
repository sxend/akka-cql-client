package arimitsu.sf.cql.v3.messages.results;

import arimitsu.sf.cql.v3.messages.Result;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 2014/06/11.
 */
public class Void implements Result {
    public static final Void INSTANCE = new Void();

    private Void() {
    }

    @Override
    public Kind getKind() {
        return Kind.VOID;
    }

    public static final ResultParser<Void> PARSER = new ResultParser<Void>() {
        @Override
        public Void parse(ByteBuffer body) {
            return Void.INSTANCE;
        }
    };
}

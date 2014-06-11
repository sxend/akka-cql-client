package arimitsu.sf.cql.v3.messages.results;

import arimitsu.sf.cql.v3.messages.Result;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 2014/06/11.
 */
public class Rows implements Result {
    // <metadata><rows_count><rows_content>
    @Override
    public Kind getKind() {
        return Kind.ROWS;
    }

    public static final ResultParser<Rows> PARSER = new ResultParser<Rows>() {
        @Override
        public Rows parse(ByteBuffer body) {
            return new Rows();
        }
    };
}

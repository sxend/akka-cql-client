package arimitsu.sf.cql.v3.messages;

import arimitsu.sf.cql.v3.Parser;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/07.
 */
public class Ready {
    private static Ready INSTANCE = new Ready();
    public static final Parser<Ready> ReadyParser = new Parser<Ready>() {
        @Override
        public Ready parse(ByteBuffer body) {
            return INSTANCE;
        }
    };
}

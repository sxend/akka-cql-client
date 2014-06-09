package arimitsu.sf.cql.v3.messages;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/07.
 */
public class Ready {
    public static final ResponseParser<Ready> ReadyParser = new ResponseParser<Ready>() {
        @Override
        public Ready parse(ByteBuffer body) {
            return new Ready();
        }
    };
}

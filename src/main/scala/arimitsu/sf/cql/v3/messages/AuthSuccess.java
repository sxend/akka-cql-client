package arimitsu.sf.cql.v3.messages;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/07.
 */
public class AuthSuccess {
    public static final Parser<AuthSuccess> PARSER = new Parser<AuthSuccess>() {
        @Override
        public AuthSuccess parse(ByteBuffer buffer) {
            return null;
        }
    };
}

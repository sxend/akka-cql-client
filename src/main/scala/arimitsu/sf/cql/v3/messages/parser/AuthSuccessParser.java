package arimitsu.sf.cql.v3.messages.parser;

import arimitsu.sf.cql.v3.Parser;
import arimitsu.sf.cql.v3.messages.AuthSuccess;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/07/06.
 */
public class AuthSuccessParser implements Parser<AuthSuccess> {
    private AuthSuccessParser() {
    }

    public static final AuthSuccessParser INSTANCE = new AuthSuccessParser();

    @Override
    public AuthSuccess parse(ByteBuffer buffer) {
        return new AuthSuccess();
    }
}

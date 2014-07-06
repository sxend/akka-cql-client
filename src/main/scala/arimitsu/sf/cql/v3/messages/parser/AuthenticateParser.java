package arimitsu.sf.cql.v3.messages.parser;

import arimitsu.sf.cql.v3.Parser;
import arimitsu.sf.cql.v3.messages.Authenticate;
import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/07/06.
 */
public class AuthenticateParser implements Parser<Authenticate> {
    private AuthenticateParser(){}
    public static final AuthenticateParser INSTANCE = new AuthenticateParser();
    @Override
    public Authenticate parse(ByteBuffer buffer) {
        return new Authenticate(Notation.getString(buffer));
    }
}

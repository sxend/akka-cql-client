package arimitsu.sf.cql.v3.messages.parser;

import arimitsu.sf.cql.v3.Parser;
import arimitsu.sf.cql.v3.messages.Supported;
import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/07/06.
 */
public class SupportedParser implements Parser<Supported> {
    private SupportedParser() {
    }

    public static final SupportedParser INSTANCE = new SupportedParser();

    @Override
    public Supported parse(ByteBuffer buffer) {
        return new Supported(Notation.getStringMultiMap(buffer));
    }
}

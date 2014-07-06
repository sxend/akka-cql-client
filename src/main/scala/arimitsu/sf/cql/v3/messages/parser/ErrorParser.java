package arimitsu.sf.cql.v3.messages.parser;

import arimitsu.sf.cql.v3.Parser;
import arimitsu.sf.cql.v3.messages.Error;
import arimitsu.sf.cql.v3.messages.ErrorCodes;
import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/07/06.
 */
public class ErrorParser implements Parser<Error> {
    private ErrorParser() {
    }

    public static final ErrorParser INSTANCE = new ErrorParser();

    @Override
    public Error parse(ByteBuffer buffer) {
        return new Error(ErrorCodes.valueOf(buffer.getInt()), Notation.getString(buffer));
    }
}

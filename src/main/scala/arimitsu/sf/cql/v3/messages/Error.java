package arimitsu.sf.cql.v3.messages;

import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;


/**
 * Created by sxend on 14/06/07.
 */
public class Error {
    public final ErrorCodes code;
    public final String message;

    public Error(ErrorCodes code, String message) {
        this.code = code;
        this.message = message;
    }

    public Throwable toThrowable() {
        return new RuntimeException(this.code + " : " + this.message);
    }

    public static Error fromBuffer(ByteBuffer buffer) {
        return new Error(ErrorCodes.valueOf(buffer.getInt()), Notation.getString(buffer));
    }
}

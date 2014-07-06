package arimitsu.sf.cql.v3.messages;

import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/07.
 */
public class Authenticate {
    public final String className;

    public Authenticate(String className) {
        this.className = className;
    }

    public static Authenticate fromBuffer(ByteBuffer buffer) {
        return new Authenticate(Notation.getString(buffer));
    }
}

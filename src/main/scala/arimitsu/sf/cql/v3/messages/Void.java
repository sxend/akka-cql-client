package arimitsu.sf.cql.v3.messages;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 2014/06/11.
 */
public class Void implements Result {
    public static final Void INSTANCE = new Void();

    private Void() {
    }

    @Override
    public Kind getKind() {
        return Kind.VOID;
    }

    public static Void fromBuffer(ByteBuffer buffer) {
        return Void.INSTANCE;
    }
}

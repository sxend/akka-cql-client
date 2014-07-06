package arimitsu.sf.cql.v3.messages;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/07.
 */
public class Ready {
    private Ready() {
    }

    private static final Ready INSTANCE = new Ready();

    public static Ready fromBuffer(ByteBuffer body) {
        return INSTANCE;
    }
}

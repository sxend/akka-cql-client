package arimitsu.sf.cql.v3.util;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 14/06/08.
 */
public class BufferUtil {
    public static int length(ByteBuffer buffer) {
        return buffer.limit() - buffer.position();
    }
}

package arimitsu.sf.cql.v3.messages;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 2014/06/17.
 */
public interface Parser<A> {
    A parse(ByteBuffer buffer);
}

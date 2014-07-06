package arimitsu.sf.cql.v3.messages;

import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;


/**
 * Created by sxend on 2014/06/11.
 */
public class SetKeyspace implements Result {

    public final String keySpace;

    public SetKeyspace(String keySpace) {
        this.keySpace = keySpace;
    }

    @Override
    public Kind getKind() {
        return Kind.SET_KEYSPACE;
    }

    public static SetKeyspace fromBuffer(ByteBuffer buffer) {
        return new SetKeyspace(Notation.getString(buffer));
    }
}

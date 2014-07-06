package arimitsu.sf.cql.v3.messages;

import arimitsu.sf.cql.v3.util.Notation;

import java.nio.ByteBuffer;

/**
 * Created by sxend on 2014/06/11.
 */
public class Prepared implements Result {
    // <id><metadata><result_metadata>
    public final byte[] id;
    public final Metadata metadata;
    public final Metadata resultMetadata;

    public Prepared(byte[] id, Metadata metadata, Metadata resultMetadata) {
        this.id = id;
        this.metadata = metadata;
        this.resultMetadata = resultMetadata;
    }

    @Override
    public Kind getKind() {
        return Kind.PREPARED;
    }

    public static Prepared fromBuffer(ByteBuffer buffer) {
        return new Prepared(Notation.getShortBytes(buffer), Metadata.fromBuffer(buffer), Metadata.fromBuffer(buffer));
    }
}

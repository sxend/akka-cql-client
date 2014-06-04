package arimitsu.sf.cql.v3;

/**
 * Created by sxend on 14/06/04.
 */
public class Header {
    public final Version version;
    public final byte flags;
    public final short streamId;
    public final Opcode opcode;

    Header(Version version, byte flags, short streamId, Opcode opcode) {
        this.version = version;
        this.flags = flags;
        this.streamId = streamId;
        this.opcode = opcode;
    }
}
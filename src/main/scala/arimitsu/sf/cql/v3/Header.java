package arimitsu.sf.cql.v3;


/**
 * Created by sxend on 14/06/04.
 */
public class Header {
    public final Version version;
    public final Flags flags;
    public final Short streamId;
    public final Opcode opcode;

    public Header(Version version, Flags flags, Short streamId, Opcode opcode) {
        this.version = version;
        this.flags = flags;
        this.streamId = streamId;
        this.opcode = opcode;
    }
}

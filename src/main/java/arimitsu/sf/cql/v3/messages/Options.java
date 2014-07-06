package arimitsu.sf.cql.v3.messages;


import arimitsu.sf.cql.v3.Flags;
import arimitsu.sf.cql.v3.Frame;
import arimitsu.sf.cql.v3.Header;
import arimitsu.sf.cql.v3.Opcode;
import arimitsu.sf.cql.v3.Version;

/**
 * Created by sxend on 14/06/07.
 */
public class Options implements Request {
    public final short streamId;
    public final Flags flags;

    public Options(short streamId, Flags flags) {
        this.streamId = streamId;
        this.flags = flags;
    }

    @Override
    public Frame toFrame() {
        return new Frame(new Header(Version.REQUEST, this.flags, this.streamId, Opcode.OPTIONS), null);
    }

}

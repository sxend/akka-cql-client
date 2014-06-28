package arimitsu.sf.cql.v3.messages;

import arimitsu.sf.cql.v3.Flags;
import arimitsu.sf.cql.v3.Frame;
import arimitsu.sf.cql.v3.Header;
import arimitsu.sf.cql.v3.Opcode;
import arimitsu.sf.cql.v3.Version;
import arimitsu.sf.cql.v3.util.Notation;

import java.util.List;

/**
 * Created by sxend on 14/06/07.
 */
public class Register implements Request {
    public final short streamId;
    public final Flags flags;
    public final List<String> events;

    public Register(short streamId, Flags flags, List<String> events) {
        this.streamId = streamId;
        this.flags = flags;
        this.events = events;
    }

    @Override
    public Frame toFrame() {
        return new Frame(new Header(Version.REQUEST, flags, streamId, Opcode.REGISTER), Notation.toStringList(events));
    }
}

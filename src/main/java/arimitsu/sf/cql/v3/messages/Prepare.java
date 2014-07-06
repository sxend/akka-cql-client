package arimitsu.sf.cql.v3.messages;

import arimitsu.sf.cql.v3.Flags;
import arimitsu.sf.cql.v3.Frame;
import arimitsu.sf.cql.v3.Header;
import arimitsu.sf.cql.v3.Opcode;
import arimitsu.sf.cql.v3.Version;
import arimitsu.sf.cql.v3.util.Notation;

/**
 * Created by sxend on 14/06/07.
 */
public class Prepare implements Request {
    public final short streamId;
    public final Flags flags;
    public final String query;

    public Prepare(short streamId, Flags flags, String query) {
        this.streamId = streamId;
        this.flags = flags;
        this.query = query;
    }

    @Override
    public Frame toFrame() {
        return new Frame(new Header(Version.REQUEST, flags, streamId, Opcode.PREPARE), Notation.toLongString(query));
    }
}

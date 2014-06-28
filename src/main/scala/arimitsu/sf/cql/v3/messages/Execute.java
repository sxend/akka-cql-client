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
public class Execute implements Request {
    public final short streamId;
    public final Flags flags;
    public final byte[] id;
    public final QueryParameters parameters;

    public Execute(short streamId, Flags flags, byte[] id, QueryParameters parameters) {
        this.streamId = streamId;
        this.flags = flags;
        this.id = id;
        this.parameters = parameters;
    }

    @Override
    public Frame toFrame() {
        return new Frame(new Header(Version.REQUEST, flags, streamId, Opcode.EXECUTE), Notation.join(id, parameters.toBytes()));
    }
}
